package com.ideas2it.dvdstore.service;

import java.time.Period;
import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.LineItem;
import com.ideas2it.dvdstore.model.PurchaseOrder;

/**
 * Provides an interface to access the DVD CollectionDAO methods
 * and to calculate the elapsed time between today's date and the release date 
 * of the dvd
 *
 * @author Ganesh Venkat S 
 */
public interface LineItemService {

    // boolean isDuplicate(Dvd dvd) throws DvdStoreException; 

    /**
     * Adds the new line item
     * 
     * @param line item
     *        new line item to be added 
     * @return 
     *        new line item, if added, null otherwise
     */
    LineItem addLineItem(LineItem lineItem) throws DvdStoreException;

}























    /*

    LineItem findLineItemById(Integer id) throws DvdStoreException;

    LineItem updateLineItemById(Integer id, LineItem lineItem) throws DvdStoreException;

    boolean removeLineItemById(Integer id) throws DvdStoreException;

    // boolean recoverLineItem(Integer id) throws DvdStoreException;

    List<LineItem> getLineItems() throws DvdStoreException;

    */






