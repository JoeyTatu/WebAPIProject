/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankproject;

/**
 *
 * @author x15015556
 */
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/customers/{customer_id}/accounts/{account_id}/withdrawals")
public class WithdrawalService {

    EntityManager entityManager;

    public WithdrawalService() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        entityManager = emfactory.createEntityManager();
    }

//    @GET
//    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Response getPlanets() {
//  
//        List<Planet> list = allEntries();
//
//        return Response.ok(list).build();
//    
//    }
//    
    @GET
    @Path("bank")
    public Response get() {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(10000);
        System.out.println("\n\n\n\n+go");
        return Response.ok("Some Data").cacheControl(cc).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getWithdrawals() {

        List<BankWithdrawal> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankWithdrawal>>(list) {
        };
        return Response.ok(entity).build();

    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllWithdrawals() {

        List<BankWithdrawal> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankWithdrawal>>(list) {
        };
        return Response.ok(entity).build();

    }

    public List<BankWithdrawal> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BankWithdrawal> cq = cb.createQuery(BankWithdrawal.class);
        Root<BankWithdrawal> rootEntry = cq.from(BankWithdrawal.class);
        CriteriaQuery<BankWithdrawal> all = cq.select(rootEntry);
        TypedQuery<BankWithdrawal> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

//    @GET
//    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
//    @Path("{id}")
//    public Planet getPlanet(@PathParam("id") int id) {
//        Planet test = entityManager.find(Planet.class, id);
//        return test;
//    }
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{withdrawal_id}")
    public BankWithdrawal getWithdrawal(@PathParam("withdrawal_id") int withdrawal_id) {
        BankWithdrawal test = entityManager.find(BankWithdrawal.class, withdrawal_id);
        if (test == null) {
            throw new NotFoundException();
        }
        return test;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/search")
    public BankWithdrawal getSearchWithdrawal(@Context UriInfo info) {
        int withdrawal_id = Integer.parseInt(info.getQueryParameters().getFirst("withdrawal_id"));

        BankWithdrawal test = entityManager.find(BankWithdrawal.class, withdrawal_id);
        if (test == null) {
            throw new NotFoundException();
        }
        return test;
    }

    @GET
    @Path("/save")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response save(@Context UriInfo info) {

        BankWithdrawal bw = new BankWithdrawal();

        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("customer_id"));
        int account_id = Integer.parseInt(info.getQueryParameters().getFirst("account_id"));
        int transaction_id = 0;

        String to_card = info.getQueryParameters().getFirst("to_card");
        String card_encryption = "";
        for (int i = to_card.length() - 1; i >= 0; i--) {
            card_encryption = card_encryption + to_card.charAt(i);
        }

        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String transaction_ref = new String(array, Charset.forName("UTF-8"));

        double amount = Double.parseDouble(info.getQueryParameters().getFirst("amount"));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date currDate = new Date();
        String date = dateFormat.format(currDate);
        String time = timeFormat.format(currDate);
        boolean verified = false;

        bw.setCustomer_id(customer_id);
        bw.setAccount_id(account_id);
        bw.setTransaction_id(transaction_id);
        bw.setTo_card(to_card);
        bw.setCard_encryption(card_encryption);
        bw.setTransacrion_ref(transaction_ref);
        bw.setAmount(amount);
        bw.setDate(date);
        bw.setTime(time);

        bw.setVerified(verified);

        System.out.println(bw);

        System.out.println();
        entityManager.getTransaction().begin();

        entityManager.persist(bw);
        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManager.close();

        return Response.status(200).entity(bw).build();
    }

    //@ref: https://www.objectdb.com/java/jpa/persistence/update
    //@ref: https://thoughts-on-java.org/persist-save-merge-saveorupdate-whats-difference-one-use/
    //@ref: https://stackoverflow.com/questions/8307578/what-is-the-best-way-to-update-the-entity-in-jpa
    @GET
    @Path("/update")
    //@Consumes(MediaType.APPLICATION_JSON)
    // @Produces({MediaType.APPLICATION_JSON})
    public Response update(@Context UriInfo info) {

        int withdrawal_id = Integer.parseInt(info.getQueryParameters().getFirst("withdrawal_id"));
        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("customer_id"));
        int account_id = Integer.parseInt(info.getQueryParameters().getFirst("account_id"));
        double amount = Double.parseDouble(info.getQueryParameters().getFirst("amount"));
        int transaction_id = 0;

        BankWithdrawal removedWithdrawal = entityManager.find(BankWithdrawal.class, withdrawal_id);
        if (removedWithdrawal == null) {
            throw new NotFoundException();
        }

        String to_card = removedWithdrawal.getTo_card();
        String card_encryption = removedWithdrawal.getCard_encryption();
        String transaction_ref = removedWithdrawal.getTransacrion_ref();

        String date = removedWithdrawal.getDate();
        String time = removedWithdrawal.getTime();
        boolean verified = removedWithdrawal.isVerified();

        BankWithdrawal newWithdrawal = removedWithdrawal;

        newWithdrawal.setCustomer_id(customer_id);
        newWithdrawal.setAccount_id(account_id);
        newWithdrawal.setTransaction_id(transaction_id);
        newWithdrawal.setTo_card(to_card);
        newWithdrawal.setCard_encryption(card_encryption);
        newWithdrawal.setTransacrion_ref(transaction_ref);
        newWithdrawal.setAmount(amount);
        newWithdrawal.setDate(date);
        newWithdrawal.setTime(time);
        newWithdrawal.setVerified(verified);

        entityManager.getTransaction().begin();
        entityManager.remove(removedWithdrawal);
        entityManager.persist(newWithdrawal);
        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManager.close();

        String message = "Record updated";

        return Response.status(200).entity(message).build();
    }

    //@ref: https://www.objectdb.com/java/jpa/persistence/update
    //@ref: https://thoughts-on-java.org/persist-save-merge-saveorupdate-whats-difference-one-use/
    //@ref: https://stackoverflow.com/questions/8307578/what-is-the-best-way-to-update-the-entity-in-jpa
    @GET
    @Path("/delete")
    //@Consumes(MediaType.APPLICATION_JSON)
    // @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@Context UriInfo info) {

        int withdrawal_id = Integer.parseInt(info.getQueryParameters().getFirst("withdrawal_id"));

        BankWithdrawal withdrawal = entityManager.find(BankWithdrawal.class, withdrawal_id);
        if (withdrawal == null) {
            throw new NotFoundException();
        }

        entityManager.getTransaction().begin();
        entityManager.remove(withdrawal);

        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManager.close();

        String message = "Record deleted";

        return Response.status(200).entity(message).build();
    }
}
