package com.ideas2it.dvdstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.PurchaseOrder;

import static com.ideas2it.dvdstore.common.Constants.*;

@Entity
@Table(name="line_item")
public class LineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "dvd_id")
    @Cascade(CascadeType.SAVE_UPDATE)
    private Dvd dvd;

    public Integer getId() {
        return this.id;
    }

    public PurchaseOrder getPurchaseOrder() {
        return this.purchaseOrder;
    }

    public Dvd getDvd() {
        return this.dvd;
    }

    public Double getPrice() {
        return this.price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public void setDvd(Dvd dvd) {
        this.dvd = dvd;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override 
    public String toString() {
        return new StringBuilder("Line Item Id: \t").append(this.id)
            .append(COMMA).append("Dvd price: \t").append(this.price)
            .append(COMMA).append("Dvd quantity: \t").append(this.quantity).toString();            
    }

}
