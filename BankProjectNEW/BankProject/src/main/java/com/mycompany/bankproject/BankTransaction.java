/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bankproject;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author suren
 */
@Entity
@Table
@Produces(MediaType.APPLICATION_JSON)
public class BankTransaction implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int transaction_id;
//    private int customer_id;
//    private int account_id;
//    private double balance;
//    
//    @OneToMany
//    @JoinColumn(name = "account_id")
//    private List<BankLodgement> lodgements;
//    
//    @OneToMany
//    @JoinColumn(name = "account_id")
//    private List<BankWithdrawal> withdrawals;
//    
//    @OneToMany
//    @JoinColumn(name = "account_id")
//    private List<BankTransfer> bank_transfers;
//    
//    @OneToMany
//    @JoinColumn(name = "current_balance")
//    private List<BankAccount> current_balance;
//
//   
//    public static void main(String[] args) {
//        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
//        EntityManager entitymanager = emfactory.createEntityManager();
//        entitymanager.getTransaction().begin();
//
//        BankTransaction transaction = new BankTransaction();
//
//        entitymanager.persist(transaction);
//
//        entitymanager.getTransaction().commit();
//        entitymanager.close();
//        emfactory.close();
//    }
//
//    public BankTransaction() {
//    }
//
//    public BankTransaction(int transaction_id, int customer_id, int account_id, double balance, List<BankLodgement> lodgements, List<BankWithdrawal> withdrawals, List<BankTransfer> bank_transfers, List<BankAccount> current_balance) {
//        this.transaction_id = transaction_id;
//        this.customer_id = customer_id;
//        this.account_id = account_id;
//        this.balance = balance;
//        this.lodgements = lodgements;
//        this.withdrawals = withdrawals;
//        this.bank_transfers = bank_transfers;
//        this.current_balance = current_balance;
//    }
//
//    public int getTransaction_id() {
//        return transaction_id;
//    }
//
//    public void setTransaction_id(int transaction_id) {
//        this.transaction_id = transaction_id;
//    }
//
//    public int getCustomer_id() {
//        return customer_id;
//    }
//
//    public void setCustomer_id(int customer_id) {
//        this.customer_id = customer_id;
//    }
//
//    public int getAccount_id() {
//        return account_id;
//    }
//
//    public void setAccount_id(int account_id) {
//        this.account_id = account_id;
//    }
//
//    public double getBalance() {
//        return balance;
//    }
//
//    public void setBalance(double balance) {
//        this.balance = balance;
//    }
//
//    public List<BankLodgement> getLodgements() {
//        return lodgements;
//    }
//
//    public void setLodgements(List<BankLodgement> lodgements) {
//        this.lodgements = lodgements;
//    }
//
//    public List<BankWithdrawal> getWithdrawals() {
//        return withdrawals;
//    }
//
//    public void setWithdrawals(List<BankWithdrawal> withdrawals) {
//        this.withdrawals = withdrawals;
//    }
//
//    public List<BankTransfer> getBank_transfers() {
//        return bank_transfers;
//    }
//
//    public void setBank_transfers(List<BankTransfer> bank_transfers) {
//        this.bank_transfers = bank_transfers;
//    }
//
//    public List<BankAccount> getCurrent_balance() {
//        return current_balance;
//    }
//
//    public void setCurrent_balance(List<BankAccount> current_balance) {
//        this.current_balance = current_balance;
//    }
//
//    @Override
//    public String toString() {
//        return "BankTransaction{" + "transaction_id=" + transaction_id + ", customer_id=" + customer_id + ", account_id=" + account_id + ", balance=" + balance + ", lodgements=" + lodgements + ", withdrawals=" + withdrawals + ", bank_transfers=" + bank_transfers + ", current_balance=" + current_balance + '}';
//    }
//
//
//   

}
