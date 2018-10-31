package com.ideas2it.dvdstore.service;

import java.time.Period;
import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;

/**
 * Provides an interface to access the DVD CollectionDAO methods
 * and to calculate the elapsed time between today's date and the release date 
 * of the dvd
 *
 * @author Ganesh Venkat S 
 */
public interface DvdCollectionService {

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
    List<Dvd> getDvd(String movieName) throws DvdStoreException;

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
     * Gets and returns the genre whose id matches id given by user 
     *
     * @param id
     *        id of the genre that the user wants details of
     * @return
     *        genre user is searching for if search is performed, null otherwise
     */
    Genre getGenreById(Integer id) throws DvdStoreException;

    /**
     * Calculates the elapsed time between the dvd release date and current date
     *
     * @param dvd
     *        dvd whose elapsed time is to be calculated
     * @return 
     *        period object representing elapsed time in years, months and days 
     */
    Period calculateElapsedTime(Dvd dvd) throws DvdStoreException;

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
    Dvd modifyDvdDetails (Integer id, Dvd dvd) throws DvdStoreException;

    /**
     * Removes the dvd, whose id matches id given by user, 
     * from the dvdCollection
     *
     * @param id
     *        id of the dvd that the user wants to delete
     * @return
     *        true if the delete is performed, false otherwise  
     */
    boolean removeDvdById(Integer id) throws DvdStoreException;

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
    List<Dvd> retrieveDvdCollection() throws DvdStoreException;

    /**
     * Returns the genreCatalogue as a list
     */
    List<Genre> retrieveGenreCatalogue() throws DvdStoreException;

}






