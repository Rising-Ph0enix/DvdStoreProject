package com.ideas2it.dvdstore.dao;

import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.PurchaseOrder;

public interface PurchaseOrderDAO { 

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
     * Removes the purchase order, whose id matches id given by user, 
     *
     * @param id
     *        id of the dvd that the user wants to delete
     * @return
     *        true if the delete is performed, false otherwise  
     */
    boolean deletePurchaseOrderById(Integer id) throws DvdStoreException;

    /**
     * Returns the list of purchase orders
     */ 
    List<PurchaseOrder> getPurchaseOrders(Integer customerId) throws DvdStoreException;

}


