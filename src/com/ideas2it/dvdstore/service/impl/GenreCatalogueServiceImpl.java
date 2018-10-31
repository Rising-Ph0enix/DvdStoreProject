package com.ideas2it.dvdstore.service.impl;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.dvdstore.dao.DvdCollectionDAO;
import com.ideas2it.dvdstore.dao.GenreCatalogueDAO;
import com.ideas2it.dvdstore.dao.impl.DvdCollectionDAOImpl;
import com.ideas2it.dvdstore.dao.impl.GenreCatalogueDAOImpl;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.service.DvdCollectionService;
import com.ideas2it.dvdstore.service.GenreCatalogueService;
import com.ideas2it.dvdstore.service.impl.GenreCatalogueServiceImpl;
import com.ideas2it.dvdstore.utils.DateHelper;

/**
 * 
 *
 * @author Ganesh Venkat S 
 */
public class GenreCatalogueServiceImpl implements GenreCatalogueService {
    
    @Override
    public Genre addGenre(Genre genre) throws DvdStoreException {
        GenreCatalogueDAO genreCatalogueDAO = new GenreCatalogueDAOImpl();
        return genreCatalogueDAO.addGenre(genre);
    }

    @Override
    public Genre getGenreDetails(String name) throws DvdStoreException {
        GenreCatalogueDAO genreCatalogueDAO = new GenreCatalogueDAOImpl();
        // Returns an empty list or null -check 
        // if there is no genre matching id given by user 
        return genreCatalogueDAO.getGenreByName(name);
    }

    @Override
    public Genre getGenreById(Integer id) throws DvdStoreException {
        GenreCatalogueDAO genreCatalogueDAO = new GenreCatalogueDAOImpl();
        // Returns an empty list or null -check 
        // if there is no genre matching id given by user 
        return genreCatalogueDAO.getGenreById(id);
    }

    @Override
    public Genre modifyGenreDetails (Integer id, Genre genre) 
            throws DvdStoreException {
        GenreCatalogueDAO genreCatalogueDAO = new GenreCatalogueDAOImpl();
        return genreCatalogueDAO.updateGenreById(id, genre);
    }

    @Override
    public boolean removeGenreById(Integer id) throws DvdStoreException {
        GenreCatalogueDAO genreCatalogueDAO = new GenreCatalogueDAOImpl();
        return genreCatalogueDAO.deleteGenreById(id);     
    }

    @Override
    public boolean recoverGenre(Integer id) throws DvdStoreException {
        GenreCatalogueDAO genreCatalogueDAO = new GenreCatalogueDAOImpl();
        return genreCatalogueDAO.recoverGenre(id);     
    }

    @Override
    public List<Genre> retrieveGenreCatalogue() throws DvdStoreException {
        GenreCatalogueDAO genreCatalogueDAO = new GenreCatalogueDAOImpl();
        return genreCatalogueDAO.getGenreCatalogue();
    }

}


