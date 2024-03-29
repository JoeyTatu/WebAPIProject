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

@Path("customers/{customer_id}/accounts")
public class AccountService {
    
    EntityManager entityManager;
    
    public AccountService(){
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
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccounts() {
  
        List<BankAccount> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankAccount>>(list){};
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
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("{account_id}")
    public BankAccount getAccount(@PathParam("account_id") int account_id) {
        BankAccount test = entityManager.find(BankAccount.class, account_id);
        if (test == null){
            throw new NotFoundException("You dun goofed");
        }
        return test;
    }
     

    
    @GET
    @Path("/save")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Context UriInfo info) {
        
        
        String sort_code = info.getQueryParameters().getFirst("sort_code");
        String account_number = info.getQueryParameters().getFirst("account_number");
        String current_balance = info.getQueryParameters().getFirst("current_balance");
        String type = info.getQueryParameters().getFirst("type");
        String customer_id = info.getQueryParameters().getFirst("customer_id");
        
        BankAccount ba = new BankAccount();
       
        ba.setSort_code(sort_code);
        ba.setAccount_number(account_number);
        ba.setCurrent_balance(current_balance);
        ba.setType(type);
        ba.setCustomer_id(Integer.parseInt(customer_id)); 
        
       ArrayList<BankTransaction> transactions = new ArrayList<BankTransaction>();
        
        ba.setTransactions(transactions);
           
        System.out.println(ba);
        
        System.out.println("........................");
        entityManager.getTransaction().begin();

        entityManager.persist(ba);
        entityManager.getTransaction().commit();
        
        entityManager.close();
//        entityManager.close();

        return Response.status(200).entity(ba).build();
    }
}
