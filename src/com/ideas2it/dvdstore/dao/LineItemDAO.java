package com.ideas2it.dvdstore.dao;

import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.LineItem;

/**
 * Performs basic CRUD operations on the Dvd Collection:
 * Add new Dvd; Search, Modify, Update and Delete Dvd by id
 *
 * @author Ganesh Venkat S
 */

public interface LineItemDAO { 

    // boolean isDuplicate(Dvd dvd) throws DvdStoreException; 
    
    /**
     * Adds the new line item to the dvdCollection
     * 
     * @param line item
     *        new line item to be added 
     * @return 
     *        new line item, if added, null otherwise
     */
    LineItem addLineItem(LineItem lineItem) throws DvdStoreException;


}























    /*
    LineItem getLineItemById(Integer id) throws DvdStoreException;

    LineItem updateLineItemById(Integer id, LineItem lineItem) throws DvdStoreException;

    boolean deleteLineItemById(Integer id) throws DvdStoreException;

    // boolean recoverLineItem(Integer id) throws DvdStoreException;

    List<LineItem> getLineItems() throws DvdStoreException;

    */












