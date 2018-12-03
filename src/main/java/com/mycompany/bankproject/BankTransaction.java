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
    private String transaction_ref;
    private int customer_id;
    private int account_id;
    private String account_number;
    private int lodgement_id;
    private int withdrawal_id;
    private int bank_transfer_id;
    private double balance;
    private double debit_amount;
    private double credit_amount;

    
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

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_ref() {
        return transaction_ref;
    }

    public void setTransaction_ref(String transaction_ref) {
        this.transaction_ref = transaction_ref;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public int getLodgement_id() {
        return lodgement_id;
    }

    public void setLodgement_id(int lodgement_id) {
        this.lodgement_id = lodgement_id;
    }

    public int getWithdrawal_id() {
        return withdrawal_id;
    }

    public void setWithdrawal_id(int withdrawal_id) {
        this.withdrawal_id = withdrawal_id;
    }

    public int getBank_transfer_id() {
        return bank_transfer_id;
    }

    public void setBank_transfer_id(int bank_transfer_id) {
        this.bank_transfer_id = bank_transfer_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getDebit_amount() {
        return debit_amount;
    }

    public void setDebit_amount(double debit_amount) {
        this.debit_amount = debit_amount;
    }

    public double getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(double credit_amount) {
        this.credit_amount = credit_amount;
    }

}
