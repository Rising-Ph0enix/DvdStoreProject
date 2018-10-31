package com.ideas2it.dvdstore.dao;

import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.User;

/**
 * Performs basic CRUD operations on the Dvd Collection:
 * Add new Dvd; Search, Modify, Update and Delete Dvd by id
 *
 * @author Ganesh Venkat S
 */

public interface UserDAO { 

    // boolean isDuplicate(Dvd dvd) throws DvdStoreException; 
    
    /**
     * Adds the new user 
     * 
     * @param user
     *        new user to be added 
     * @return 
     *        new user, if added, null otherwise
     */
    User addUser(User user) throws DvdStoreException;

    /**
     * Authenticates the user 
     * 
     * @param emailId
     *        emailId of user
     * @param password
     *        password of user  
     * @return 
     *        new user, if added, null otherwise
     */
    User authenticateUser(String emailId, String password) throws DvdStoreException;

}
