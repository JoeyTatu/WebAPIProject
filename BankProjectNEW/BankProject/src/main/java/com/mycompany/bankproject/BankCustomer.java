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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

@Entity
@Table
@Produces(MediaType.APPLICATION_JSON)
//@XmlRootElement
public class BankCustomer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customer_id;
    private String first_name;
    private String last_name;
    private String address;
    private String email;
    private String phone_number;
    private boolean verification;
    private String encrypted_password;
    private int pin;
    private String status;
    
    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<BankAccount> accounts;

//    }
    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        BankCustomer cust = new BankCustomer();

        entitymanager.persist(cust);

        entitymanager.getTransaction().commit();
        entitymanager.close();
        emfactory.close();
    }

    public BankCustomer() {
    }

    public BankCustomer(int customer_id, String first_name, String last_name, String address, String email, String phone_number, boolean verification, String encrypted_password, int pin, String status, List<BankAccount> accounts) {
        this.customer_id = customer_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
        this.verification = verification;
        this.encrypted_password = encrypted_password;
        this.pin = pin;
        this.status = status;
        this.accounts = accounts;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isVerification() {
        return verification;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
    }

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "BankCustomer{" + "customer_id=" + customer_id + ", first_name=" + first_name + ", last_name=" + last_name + ", address=" + address + ", email=" + email + ", phone_number=" + phone_number + ", verification=" + verification + ", encrypted_password=" + encrypted_password + ", pin=" + pin + ", status=" + status + ", accounts=" + accounts + '}';
    }
    
    

}
