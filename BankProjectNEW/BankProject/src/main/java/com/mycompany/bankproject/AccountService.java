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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

@Path("/customers/{customer_id}/accounts")
public class AccountService {

    EntityManager entityManager;
//    
//    public AccountService(){
//        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
//        entityManager = emfactory.createEntityManager();
//    }

    public AccountService() {
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccounts() {

        List<BankAccount> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankAccount>>(list) {
        };
        return Response.ok(entity).build();

    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllAccounts() {

        List<BankAccount> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankAccount>>(list) {
        };
        return Response.ok(entity).build();

    }

    public List<BankAccount> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BankAccount> cq = cb.createQuery(BankAccount.class);
        Root<BankAccount> rootEntry = cq.from(BankAccount.class);
        CriteriaQuery<BankAccount> all = cq.select(rootEntry);
        TypedQuery<BankAccount> allQuery = entityManager.createQuery(all);
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
    @Path("{account_id}")
    public BankAccount getAccount(@PathParam("account_id") int account_id) {
        BankAccount test = entityManager.find(BankAccount.class, account_id);
        if (test == null) {
            throw new NotFoundException();
        }
        return test;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/search")
    public BankAccount getSearchAccount(@Context UriInfo info) {
        int account_id = Integer.parseInt(info.getQueryParameters().getFirst("account_id"));

        BankAccount test = entityManager.find(BankAccount.class, account_id);
        if (test == null) {
            throw new NotFoundException();
        }
        return test;
    }

    @GET
    //@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/balance")
    public String getgetBalance(@Context UriInfo info) {
        int account_id = Integer.parseInt(info.getQueryParameters().getFirst("account_id"));

        BankAccount account = entityManager.find(BankAccount.class, account_id);
        if (account == null) {
            throw new NotFoundException();
        }
        double current_balance = account.getCurrent_balance();
        return "The current balance of account with account_id: " + account_id + " is: €" + current_balance;
    }

    @GET
    @Path("/save")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Context UriInfo info) {

        String sort_code = info.getQueryParameters().getFirst("sort_code");
        String account_number = info.getQueryParameters().getFirst("account_number");
        double current_balance = Double.parseDouble(info.getQueryParameters().getFirst("current_balance"));
        String type = info.getQueryParameters().getFirst("type");
        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("customer_id"));

        BankAccount ba = new BankAccount();

        ba.setSort_code(sort_code);
        ba.setAccount_number(account_number);
        ba.setCurrent_balance(current_balance);
        ba.setType(type);
        ba.setCustomer_id(customer_id);

        ArrayList<BankLodgement> lodgements = new ArrayList<BankLodgement>();
        ArrayList<BankWithdrawal> withdrawals = new ArrayList<BankWithdrawal>();
        ArrayList<BankTransfer> bank_transfers = new ArrayList<BankTransfer>();
        ba.setLodgements(lodgements);
        ba.setWithdrawals(withdrawals);
        ba.setBank_transfers(bank_transfers);

        System.out.println(ba);

        System.out.println("........................");
        entityManager.getTransaction().begin();

        entityManager.persist(ba);
        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManager.close();

        return Response.status(200).entity(ba).build();
    }
    
    
    //@ref: https://www.objectdb.com/java/jpa/persistence/update
    //@ref: https://thoughts-on-java.org/persist-save-merge-saveorupdate-whats-difference-one-use/
    //@ref: https://stackoverflow.com/questions/8307578/what-is-the-best-way-to-update-the-entity-in-jpa
    @GET
    @Path("/update")
    //@Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response update(@Context UriInfo info) {

        int account_id = Integer.parseInt(info.getQueryParameters().getFirst("update_account_id"));
        String sort_code = info.getQueryParameters().getFirst("update_sort_code");
        String account_number = info.getQueryParameters().getFirst("update_account_number");
        double current_balance = Double.parseDouble(info.getQueryParameters().getFirst("update_current_balance"));
        String type = info.getQueryParameters().getFirst("update_type");
        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("update_customer_id"));

        System.out.println("");

        BankAccount removedAccount = entityManager.find(BankAccount.class, account_id);
        if (removedAccount == null) {
            throw new NotFoundException();
        }

        List lodgements = removedAccount.getLodgements();
        List withdrawals = removedAccount.getWithdrawals();
        List bank_transfers = removedAccount.getBank_transfers();

        BankAccount newAccount = removedAccount;

        entityManager.getTransaction().begin();

        newAccount.setSort_code(sort_code);
        newAccount.setAccount_number(account_number);
        newAccount.setCurrent_balance(current_balance);
        newAccount.setType(type);
        newAccount.setCustomer_id(customer_id);
        newAccount.setLodgements(lodgements);
        newAccount.setWithdrawals(withdrawals);
        newAccount.setBank_transfers(bank_transfers);

        //account.setCustomer_id(customer_id);
        entityManager.remove(removedAccount);
        entityManager.persist(newAccount);
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
    //@Produces(MediaType.APPLICATION_JSON)
    public Response delete(@Context UriInfo info) {

        int account_id = Integer.parseInt(info.getQueryParameters().getFirst("delete_account_id"));
        System.out.println("");

        BankAccount account = entityManager.find(BankAccount.class, account_id);
        if (account == null) {
            throw new NotFoundException();
        }

        entityManager.getTransaction().begin();
        entityManager.remove(account);
        entityManager.getTransaction().commit();

        entityManager.close();

        String message = "Record deleted";

        return Response.status(200).entity(message).build();
    }
}
