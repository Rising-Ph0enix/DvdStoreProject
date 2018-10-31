package com.ideas2it.dvdstore.dao;

import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Dvd;

/**
 * Performs basic CRUD operations on the Dvd Collection:
 * Add new Dvd; Search, Modify, Update and Delete Dvd by id
 *
 * @author Ganesh Venkat S
 */

public interface DvdCollectionDAO { 

    boolean isDuplicate(Dvd dvd) throws DvdStoreException; 

    /**
     * Adds the new dvd to the dvdCollection
     * 
     * @param dvd
     *        new dvd to be added 
     * @return 
     *        new dvd, if added, null otherwise
     */
    Dvd addDvd(Dvd dvd) throws DvdStoreException;

    /**
     * Gets and returns the list of dvds whose name matches input given by user 
     *
     * @param movieName
     *        movieName of the dvd that the user wants details of
     * @return
     *        list of dvds with same movie name as user input, if search is performed, empty list otherwise
     */
    List<Dvd> getDvdByName(String movieName) throws DvdStoreException;

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
     * Updates the details of the dvd whose id matches id given by user
     *
     * @param id
     *        id of the dvd that the user wants to update
     *
     * @param dvd
     *        dvd with updated details
     * @return
     *        updated dvd if the update is performed, null otherwise 
     */
    Dvd updateDvdById(Integer id, Dvd dvd) throws DvdStoreException;

    /**
     * Removes the dvd, whose id matches id given by user, 
     * from the dvdCollection
     *
     * @param id
     *        id of the dvd that the user wants to delete
     * @return
     *        true if the delete is performed, false otherwise  
     */
    boolean deleteDvdById(Integer id) throws DvdStoreException;

    /**
     * Returns true if the dvd is recovered, false otherwise
     *
     * @param id
     *        id of the dvd that the user wants to remove
     */
    boolean recoverDvd(Integer id) throws DvdStoreException;

    /**
     * Returns the dvdCollection as a list
     */
    List<Dvd> getDvdCollection() throws DvdStoreException;

}









