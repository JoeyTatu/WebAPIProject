/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankproject;

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
@Path("/customers/{customer_id}/accounts/{account_id}/transactions")
public class TransactionService {

    EntityManager entityManager;

    public TransactionService() {
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
    public Response getTransactions() {

        List<BankTransaction> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankTransaction>>(list) {
        };
        return Response.ok(entity).build();

    }

    public List<BankTransaction> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BankTransaction> cq = cb.createQuery(BankTransaction.class);
        Root<BankTransaction> rootEntry = cq.from(BankTransaction.class);
        CriteriaQuery<BankTransaction> all = cq.select(rootEntry);
        TypedQuery<BankTransaction> allQuery = entityManager.createQuery(all);
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
    @Path("{transaction_id}")
    public BankTransaction getTransaction(@PathParam("transaction_id") int transaction_id) {
        BankTransaction test = entityManager.find(BankTransaction.class, transaction_id);
        if (test == null) {
            throw new NotFoundException("You dun goofed");
        }
        return test;
    }

    @GET
    @Path("/save")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Context UriInfo info) {

        String date = info.getQueryParameters().getFirst("date");
        String type = info.getQueryParameters().getFirst("type");
        String amount = info.getQueryParameters().getFirst("amount");
        String post_transaction = info.getQueryParameters().getFirst("post_transaction");
        String description = info.getQueryParameters().getFirst("description");
        String account_id = info.getQueryParameters().getFirst("account_id");

        BankTransaction bt = new BankTransaction();

        bt.setDate(date);
        bt.setType(type);
        bt.setAmount(Integer.parseInt(amount));
        bt.setPost_transaction(Integer.parseInt(post_transaction));
        bt.setDescription(description);
        bt.setAccount_id(Integer.parseInt(account_id));

        System.out.println(bt);

        System.out.println("........................");
        entityManager.getTransaction().begin();

        entityManager.persist(bt);
        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManager.close();

        return Response.status(200).entity(bt).build();
    }
    /*
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(BankTransaction c) {
        System.out.println(c);
        entityManager.getTransaction().begin();

        entityManager.persist(c);
        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManager.close();

        return Response.status(200).entity(c).build();
    }
     */
}
