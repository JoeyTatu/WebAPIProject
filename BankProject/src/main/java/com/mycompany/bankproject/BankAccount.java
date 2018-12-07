/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author x15015556
 */
@Entity
@Table
@Produces(MediaType.APPLICATION_JSON)
//@XmlRootElement
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int account_id;
    private String sort_code;
    private String account_number;
    private String current_balance;
    private String type;
    private int customer_id;

    @OneToMany
    @JoinColumn(name = "account_id")
    private List<BankTransaction> transactions;

    public BankAccount() {
    }

    public BankAccount(int account_id, String sort_code, String account_number, String current_balance, String type, int customer_id, List<BankTransaction> transactions) {
        this.account_id = account_id;
        this.sort_code = sort_code;
        this.account_number = account_number;
        this.current_balance = current_balance;
        this.type = type;
        this.customer_id = customer_id;
        this.transactions = transactions;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getSort_code() {
        return sort_code;
    }

    public void setSort_code(String sort_code) {
        this.sort_code = sort_code;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(String current_balance) {
        this.current_balance = current_balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public List<BankTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<BankTransaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "account_id=" + account_id + ", sort_code=" + sort_code + ", account_number=" + account_number + ", current_balance=" + current_balance + ", type=" + type + ", customer_id=" + customer_id + ", transactions=" + transactions + '}';
    }

    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        BankAccount account = new BankAccount();

        entitymanager.persist(account);

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();

        entitymanager.persist(account);

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }
}
