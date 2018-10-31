package com.ideas2it.dvdstore.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {

    @Id  
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="email_id")
    private String emailId;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    @Column(name="expired_date")
    private LocalDate expiredDate;
  
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Integer getId() {
        return this.id;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

    public LocalDate getExpiredDate() {
        return this.expiredDate;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }        

    public void setPassword(String password) {
        this.password = password;
    }    

    public void setRole(String role) {
        this.role = role;
    }    

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String toString()
    {
        return "Email Id: " + this.emailId + "Password: " + this.password + "Role: " + this.role;
    }
}
