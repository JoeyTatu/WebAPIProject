/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankproject;

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

/**
 *
 * @author suren
 */
@Path("/customers/{customer_id}/accounts/{account_id}/lodgements")
public class LodgementService {

    EntityManager entityManager;

    public LodgementService() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        entityManager = emfactory.createEntityManager();
    }

    @GET
    @Path("bank")
    public Response get() {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(10000);
        System.out.println("\n\n\n\n+go");
        return Response.ok("Some Data").cacheControl(cc).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getLodgements() {

        List<BankLodgement> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankLodgement>>(list) {
        };
        return Response.ok(entity).build();

    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllLodgements() {

        List<BankLodgement> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankLodgement>>(list) {
        };
        return Response.ok(entity).build();

    }

    public List<BankLodgement> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BankLodgement> cq = cb.createQuery(BankLodgement.class);
        Root<BankLodgement> rootEntry = cq.from(BankLodgement.class);
        CriteriaQuery<BankLodgement> all = cq.select(rootEntry);
        TypedQuery<BankLodgement> allQuery = entityManager.createQuery(all);
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
    @Path("{lodgement_id}")
    public BankLodgement getLodgement(@PathParam("lodgement_id") int lodgement_id) {
        BankLodgement test = entityManager.find(BankLodgement.class, lodgement_id);
        if (test == null) {
            throw new NotFoundException();
        }
        return test;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/search")
    public BankLodgement getSearchLodgement(@Context UriInfo info) {
        int lodgement_id = Integer.parseInt(info.getQueryParameters().getFirst("lodgement_id"));

        BankLodgement test = entityManager.find(BankLodgement.class, lodgement_id);
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

        BankLodgement bl = new BankLodgement();
        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("customer_id"));
        int account_id = Integer.parseInt(info.getQueryParameters().getFirst("account_id"));
        int transaction_id = 0;

        String from_card = info.getQueryParameters().getFirst("from_card");
        String card_encryption = "";
        for (int i = from_card.length() - 1; i >= 0; i--) {
            card_encryption = card_encryption + from_card.charAt(i);
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

        //ArrayList<BankCustomer> customers = new ArrayList<BankCustomer>();
        // ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
        //ArrayList<BankTransaction> transactions = new ArrayList<BankTransaction>();
        bl.setCustomer_id(customer_id);
        bl.setAccount_id(account_id);
        bl.setTransaction_id(transaction_id);
        bl.setFrom_card(from_card);
        bl.setCard_encryption(card_encryption);
        bl.setTransacrion_ref(transaction_ref);
        bl.setAmount(amount);
        bl.setDate(date);
        bl.setTime(time);
        //bl.setCustomers(customers);
        //bl.setAccounts(accounts);
        bl.setVerified(verified);

        System.out.println(bl);

        System.out.println();
        entityManager.getTransaction().begin();

        entityManager.persist(bl);
        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManager.close();

        return Response.status(200).entity(bl).build();
    }

    //@ref: https://www.objectdb.com/java/jpa/persistence/update
    //@ref: https://thoughts-on-java.org/persist-save-merge-saveorupdate-whats-difference-one-use/
    //@ref: https://stackoverflow.com/questions/8307578/what-is-the-best-way-to-update-the-entity-in-jpa
    @GET
    @Path("/update")
    //@Consumes(MediaType.APPLICATION_JSON)
    //@Produces({MediaType.APPLICATION_JSON})
    public Response update(@Context UriInfo info) {

        int lodgement_id = Integer.parseInt(info.getQueryParameters().getFirst("lodgement_id"));
        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("customer_id"));
        int account_id = Integer.parseInt(info.getQueryParameters().getFirst("account_id"));
        double amount = Double.parseDouble(info.getQueryParameters().getFirst("amount"));
        int transaction_id = 0;

        BankLodgement removedLodgement = entityManager.find(BankLodgement.class, lodgement_id);
        if (removedLodgement == null) {
            throw new NotFoundException();
        }

        String date = removedLodgement.getDate();
        String time = removedLodgement.getTime();
        String from_card = removedLodgement.getFrom_card();
        String card_encryption = removedLodgement.getCard_encryption();
        String transaction_ref = removedLodgement.getTransacrion_ref();
        boolean verified = removedLodgement.isVerified();

        BankLodgement newLodgement = removedLodgement;

        newLodgement.setCustomer_id(customer_id);
        newLodgement.setAccount_id(account_id);
        newLodgement.setTransaction_id(transaction_id);
        newLodgement.setFrom_card(from_card);
        newLodgement.setCard_encryption(card_encryption);
        newLodgement.setTransacrion_ref(transaction_ref);
        newLodgement.setAmount(amount);
        newLodgement.setDate(date);
        newLodgement.setTime(time);
        newLodgement.setVerified(verified);

        entityManager.getTransaction().begin();
        entityManager.remove(removedLodgement);
        entityManager.persist(newLodgement);
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
    //@Produces({MediaType.APPLICATION_JSON})
    public Response delete(@Context UriInfo info) {

        int lodgement_id = Integer.parseInt(info.getQueryParameters().getFirst("lodgement_id"));

        BankLodgement lodgement = entityManager.find(BankLodgement.class, lodgement_id);
        if (lodgement == null) {
            throw new NotFoundException();
        }

        entityManager.getTransaction().begin();
        entityManager.remove(lodgement);

        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManager.close();
        String message = "Record deleted";
        return Response.status(200).entity(message).build();
    }
}
