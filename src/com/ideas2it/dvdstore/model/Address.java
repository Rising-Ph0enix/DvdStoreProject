package com.ideas2it.dvdstore.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="address")
public class Address {

    @Id  
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="street")
    private String street;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="pincode")
    private Integer pinCode;

    @Column(name="expired_date")
    private LocalDate expiredDate;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Integer getId() {
        return this.id;
    }

    public String getStreet() {
        return this.street;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public Integer getPinCode() {
        return this.pinCode;
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

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }    

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String toString()
    {
        return "Street: " + this.street + "\nCity: " + city + "\nState: " + state + "\nPincode: " + pinCode;
    }
}
