package com.ideas2it.dvdstore.service;

import java.time.Period;
import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.LineItem;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.model.PurchaseOrder;

/**
 * Provides an interface to access the DVD CollectionDAO methods
 * and to calculate the elapsed time between today's date and the release date 
 * of the dvd
 *
 * @author Ganesh Venkat S 
 */
public interface CustomerService {

    // boolean isDuplicate(Dvd dvd) throws DvdStoreException; 

    /**
     * Adds the new customer
     * 
     * @param customer
     *        new customer to be added 
     * @return 
     *        new customer, if added, null otherwise
     */
    Customer addCustomer(Customer customer) throws DvdStoreException;

    /**
     * Gets and returns the customer whose id matches id given by user 
     *
     * @param id
     *        id of the customer that the user wants details of
     * @return
     *        customer user is searching for if search is performed, null otherwise
     */
    Customer getCustomerById(Integer id) throws DvdStoreException;

    /**
     * Gets and returns the customer whose emailId matches id given by user 
     *
     * @param id
     *        id of the customer that the user wants details of
     * @return
     *        customer id of customer if search is performed, null otherwise
     */
    Integer getCustomerIdByUserEmailId(String emailId) throws DvdStoreException;

    /**
     * Updates the details of the customer whose id matches id given by user
     *
     * @param customer
     *        customer with updated details
     * @return
     *        updated customer if the update is performed, null otherwise 
     */
    Customer updateCustomerDetails(Customer customer) throws DvdStoreException;

    /**
     * Updates the details of the dvd whose id matches id given by user
     *
     * @param id
     *        id of the dvd to modify
     * @param updatedDvd
     *        dvd with updated details
     * @return
     *        updated customer if the update is performed, null otherwise 
     */
    Dvd updateDvdDetails(Integer id, Dvd updatedDvd) throws DvdStoreException;
   
    /**
     * Removes the customer, whose id matches id given by user
     *
     * @param id
     *        id of the customer that the user wants to delete
     * @return
     *        true if the customer is removed, false otherwise  
     */
    boolean removeCustomerById(Integer id) throws DvdStoreException;

    // boolean recoverLineItem(Integer id) throws DvdStoreException;

    /**
     * Returns the list of customers
     */ 
    List<Customer> retrieveCustomers() throws DvdStoreException;

    /**
     * Gets and returns the dvd whose id matches id given by user 
     *
     * @param id
     *        id of the dvd that the user wants details of
     * @return
     *        dvd user is searching for if search is performed, null otherwise
     */
    Dvd getDvdById(Integer id) throws DvdStoreException;

    /**
     * Adds the new User
     * 
     * @param User
     *        new User to be added 
     * @return 
     *        new User, if added, null otherwise
     */
    User addUser(User user) throws DvdStoreException;

    /**
     * Adds the new line item
     * 
     * @param lineItem
     *        new lineItem to be added 
     * @return 
     *        new LineItem, if added, null otherwise
     */
    LineItem addLineItem(LineItem lineItem) throws DvdStoreException;

    /**
     * Adds the new purchase order
     * 
     * @param purchase order
     *        new purchase order to be added 
     * @return 
     *        new purchase order, if added, null otherwise
     */
    PurchaseOrder addCustomerOrder(PurchaseOrder purchaseOrder) throws DvdStoreException;

    /**
     * Removes the order, whose id matches user input
     *
     * @param id
     *        id of the customer
     *
     * @param id
     *        id of the order that the user wants to cancel
     *
     * @return
     *        true if the customer is removed, false otherwise  
     */
    boolean cancelCustomerOrder(Integer customerId, Integer orderId) throws DvdStoreException;

    /**
     * Returns the list of purchase orders associated with a particular customer
     */
    List<PurchaseOrder> getCustomerOrders(Integer customerId) throws DvdStoreException;

}






