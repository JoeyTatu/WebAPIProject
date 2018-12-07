/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankproject;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author suren
 */
@Entity
@Table
@XmlRootElement
public class BankTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transaction_id;
    private String date;
    private String type;
    private int amount;
    private int post_transaction;
    private String description;
    private int account_id;

    public BankTransaction() {
    }

    public BankTransaction(int transaction_id, String date, String type, int amount, int post_transaction, String description, int account_id) {
        this.transaction_id = transaction_id;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.post_transaction = post_transaction;
        this.description = description;
        this.account_id = account_id;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPost_transaction() {
        return post_transaction;
    }

    public void setPost_transaction(int post_transaction) {
        this.post_transaction = post_transaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    @Override
    public String toString() {
        return "BankTransaction{" + "transaction_id=" + transaction_id + ", date=" + date + ", type=" + type + ", amount=" + amount + ", post_transaction=" + post_transaction + ", description=" + description + ", account_id=" + account_id + '}';
    }

    
    

    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        BankTransaction transaction = new BankTransaction();

        entitymanager.persist(transaction);

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }


   

}
