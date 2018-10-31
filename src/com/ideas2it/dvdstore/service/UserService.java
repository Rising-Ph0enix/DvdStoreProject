package com.ideas2it.dvdstore.service;

import java.time.Period;
import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.model.PurchaseOrder;

/**
 * Provides an interface to access the DVD CollectionDAO methods
 * and to calculate the elapsed time between today's date and the release date 
 * of the dvd
 *
 * @author Ganesh Venkat S 
 */
public interface UserService {

    // boolean isDuplicate(Dvd dvd) throws DvdStoreException; 

    /**
     * Adds the new User
     * 
     * @param User
     *        new User to be added 
     * @return 
     *        new User, if added, null otherwise
     */
    User addUser(User user) throws DvdStoreException;

    User authenticateUser(String emailId, String password) throws DvdStoreException;

    /**
     * Gets and returns the customer whose emailId matches id given by user 
     *
     * @param id
     *        id of the customer that the user wants details of
     * @return
     *        customer id of customer if search is performed, null otherwise
     */
    Integer getCustomerIdByUserEmailId(String emailId) throws DvdStoreException;

}
