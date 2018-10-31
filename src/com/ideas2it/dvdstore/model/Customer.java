package com.ideas2it.dvdstore.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.LineItem;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.model.User;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import static com.ideas2it.dvdstore.common.Constants.*;

@Entity
@Table(name="customer")
public class Customer {

    @Id  
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="expired_date")
    private LocalDate expiredDate;

    @OneToMany(mappedBy = "customer", fetch=FetchType.EAGER)
    private List<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();

    @OneToOne(mappedBy = "customer")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Address address;

    @OneToOne(mappedBy = "customer")
    @Cascade(CascadeType.SAVE_UPDATE)
    private User user;


   
    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public LocalDate getExpiredDate() {
        return this.expiredDate;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return this.purchaseOrders;
    }

    public Address getAddress() {
        return this.address;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override 
    public String toString() {
        return new StringBuilder("Customer id: ").append(this.id)
            .append(COMMA).append("Customer name: ").append(this.name)
            .append(COMMA).append("Customer phone number: ").append(this.phoneNumber)
            .append(COMMA).append("Customer address: ").append(this.address)
            .append(COMMA).append("Customer account: \n").append(this.user).toString();
    }

}
