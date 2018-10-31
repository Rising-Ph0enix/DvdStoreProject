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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.ideas2it.dvdstore.model.LineItem;

import static com.ideas2it.dvdstore.common.Constants.*;

@Entity
@Table(name="purchase_order")
public class PurchaseOrder {
    @Id  
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="ordered_date")
    private LocalDate orderedDate;

    // Need to link this order id to the customer ID - one-many from POV of customer
    // Haven't used nullable = false here bc already def in table
    // No price field for all the dvds here

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "purchaseOrder", fetch=FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<LineItem> lineItems = new ArrayList<LineItem>();

    public Integer getId() {
        return this.id;
    }

    public LocalDate getOrderedDate() {
        return this.orderedDate;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public List<LineItem> getLineItems() {
        return this.lineItems;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderedDate(LocalDate orderedDate) {
        this.orderedDate = orderedDate;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @Override 
    public String toString() {
        return new StringBuilder("Purchase Order Id: \t").append(this.id)
            .append(COMMA).append("Ordered Date: \t").append(this.orderedDate).toString();            
    }

}


