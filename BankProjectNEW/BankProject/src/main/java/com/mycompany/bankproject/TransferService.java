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

@Path("customers/{customer_id}/accounts/{account_id}/bank_transfers")
public class TransferService {
    
    EntityManager entityManager;
    
    public TransferService (){
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
    public Response getTransfers() {
  
        List<BankTransfer> list = allEntries();

        GenericEntity entity = new GenericEntity<List<BankTransfer>>(list){};
        return Response.ok(entity).build();
    
    }
    
     public List<BankTransfer> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BankTransfer> cq = cb.createQuery(BankTransfer.class);
        Root<BankTransfer> rootEntry = cq.from(BankTransfer.class);
        CriteriaQuery<BankTransfer> all = cq.select(rootEntry);
        TypedQuery<BankTransfer> allQuery = entityManager.createQuery(all);
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
    @Path("{bank_transfer_id}")
    public BankTransfer getTransfer(@PathParam("bank_transfer_id") int bank_transfer_id) {
        BankTransfer test = entityManager.find(BankTransfer.class, bank_transfer_id);
        if (test == null){
            throw new NotFoundException();
        }
        return test;
    }
     

    
   @GET
    @Path("/save")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
   
        public Response save(@Context UriInfo info) {

        BankTransfer bt = new BankTransfer();
        

        int customer_id = Integer.parseInt(info.getQueryParameters().getFirst("customer_id"));
        int account_id = Integer.parseInt(info.getQueryParameters().getFirst("account_id"));
        int transaction_id = 0;
        String to_sort_code = info.getQueryParameters().getFirst("to_sort_code");
        String to_account = info.getQueryParameters().getFirst("to_account");
//        String card_encryption = "";
//        for(int i = to_card.length() - 1; i >= 0; i--)
//        {
//            card_encryption = card_encryption + to_card.charAt(i);
//        }
       
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

        bt.setCustomer_id(customer_id);
        bt.setAccount_id(account_id);
        bt.setTransaction_id(transaction_id);
        bt.setTo_sort_code(to_sort_code);
        bt.setTo_account(to_account);
        bt.setTransacrion_ref(transaction_ref);
        bt.setAmount(amount);
        bt.setDate(date);
        bt.setTime(time);
        bt.setVerified(verified);

        System.out.println(bt);

        System.out.println();
        entityManager.getTransaction().begin();

        entityManager.persist(bt);
        entityManager.getTransaction().commit();

        entityManager.close();
//        entityManager.close();

        return Response.status(200).entity(bt).build();

    }
}
