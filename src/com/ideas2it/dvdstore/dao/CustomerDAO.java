package com.ideas2it.dvdstore.dao;

import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.PurchaseOrder;

public interface CustomerDAO { 

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
     * Removes the customer, whose id matches id given by user
     *
     * @param id
     *        id of the customer that the user wants to delete
     * @return
     *        true if the customer is removed, false otherwise  
     */
    boolean deleteCustomerById(Integer id) throws DvdStoreException;

    // boolean recoverLineItem(Integer id) throws DvdStoreException;

    // No need for 2 extra methods here - just reuse the methods in the order DAO

    /**
     * Gets and returns the order whose id matches id given by user 
     *
     * @param id
     *        id of the customer that the user wants details of
     *
     * @param id
     *        id of the order that the user wants details of
     *
     * @return
     *        customer user is searching for if search is performed, null otherwise
     */
    PurchaseOrder getCustomerOrder(Integer customerId, Integer orderId) throws DvdStoreException;

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
     * Returns the list of customers
     */
    List<Customer> getCustomers() throws DvdStoreException;

}


