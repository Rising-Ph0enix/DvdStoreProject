package com.ideas2it.dvdstore.service;

import java.time.Period;
import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.PurchaseOrder;

/**
 * Provides an interface to access the DVD CollectionDAO methods
 * and to calculate the elapsed time between today's date and the release date 
 * of the dvd
 *
 * @author Ganesh Venkat S 
 */
public interface PurchaseOrderService {

    // boolean isDuplicate(Dvd dvd) throws DvdStoreException; 

    /**
     * Adds the new purchase order
     * 
     * @param purchase order
     *        new purchase order to be added 
     * @return 
     *        new purchase order, if added, null otherwise
     */
    PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder) throws DvdStoreException;

    /**
     * Gets and returns the purchase order whose id matches id given by user 
     *
     * @param id
     *        id of the purchase order that the user wants details of
     * @return
     *        purchase order user is searching for if search is performed, null otherwise
     */
    PurchaseOrder getPurchaseOrderById(Integer id) throws DvdStoreException;

    // Business Rule states that you can't modify an order after placing it - immediate shipping

    /**
     * Removes the purchase order, whose id matches id given by user
     *
     * @param id
     *        id of the purchase order that the user wants to delete
     * @return
     *        true if the purchase order is removed, false otherwise  
     */
    boolean removePurchaseOrderById(Integer id) throws DvdStoreException;

    // boolean recoverLineItem(Integer id) throws DvdStoreException;

     /**
     * Returns the list of purchase orders, associated with a particular customer
     *
     * @param id
     *        id of the customer that the user wants orders for
     */
    List<PurchaseOrder> getPurchaseOrders(Integer customerId) throws DvdStoreException;

}






