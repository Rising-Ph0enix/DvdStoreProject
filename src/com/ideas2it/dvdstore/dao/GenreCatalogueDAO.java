package com.ideas2it.dvdstore.dao;

import java.util.List;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;

/**
 * Performs basic CRUD operations on the Genre Catalogue:
 * Add new genre; Search, Modify, Update, Delete and recover genre by id
 *
 * @author Ganesh Venkat S
 */

public interface GenreCatalogueDAO { 

    /**
     * Adds the new genre to the catalogue
     * 
     * @param name
     *        name of the genre to be added 
     * @return 
     *        added genre, if added, null otherwise
     */
    Genre addGenre(Genre genre) throws DvdStoreException;

    /**
     * Gets and returns the genre with same name as user input 
     *
     * @param name
     *        name of the genre that the user wants details of
     * @return
     *        genre with same name as user input, if searched, null otherwise
     */
    Genre getGenreByName(String name) throws DvdStoreException;
    
    /**
     * Gets and returns the genre whose id matches id given by user 
     *
     * @param id
     *        id of the genre that the user wants details of
     * @return
     *        genre with id same as user input, if searched, null otherwise
     */
    Genre getGenreById(Integer id) throws DvdStoreException;

   /**
     * Updates the details of the genre whose id matches id given by user
     *
     * @param id
     *        id of the genre that the user wants to update
     *
     * @param genre
     *        genre with updated details
     * @return
     *        Updated genre if the update is performed, null otherwise 
     */
    Genre updateGenreById(Integer id, Genre genre) 
        throws DvdStoreException;

    /**
     * Removes the genre, whose id matches id given by user, 
     * from the genre catalogue
     *
     * @param id
     *        id of the genre that the user wants to delete
     * @return
     *        true if the delete is performed, false otherwise  
     */
    boolean deleteGenreById(Integer id) throws DvdStoreException;

    /**
     * Returns true if the genre is recovered, false otherwise
     *
     * @param id
     *        id of the genre that the user wants to remove
     */
    boolean recoverGenre(Integer id) throws DvdStoreException;

    /**
     * Returns the genre catalogue, as a list of genres
     */
    List<Genre> getGenreCatalogue() throws DvdStoreException;

   
}
