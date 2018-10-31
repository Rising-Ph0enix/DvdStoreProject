package com.ideas2it.dvdstore.service.impl;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
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
import com.ideas2it.dvdstore.utils.DateHelper;

import static com.ideas2it.dvdstore.common.Constants.*;

/**
 * DvdCollection contains the entire collection of dvds and their associated genres
 *
 * @author Ganesh Venkat S 
 */
public class DvdCollectionServiceImpl implements DvdCollectionService {

    @Override
    public Dvd addDvd(Dvd dvd) throws DvdStoreException {
        DvdCollectionDAO dvdCollectionDAO = new DvdCollectionDAOImpl();
        if (dvdCollectionDAO.isDuplicate(dvd)) {
            dvd = dvdCollectionDAO.updateDvdById(dvd.getId(), dvd);
        } else {
            dvd = dvdCollectionDAO.addDvd(dvd);
        }
        return dvd;
    }

    @Override
    public List<Dvd> getDvd(String movieName) throws DvdStoreException {
        DvdCollectionDAO dvdCollectionDAO = new DvdCollectionDAOImpl();
        // Returns null if there is no dvd matching id given by user 
        return dvdCollectionDAO.getDvdByName(movieName);
    }

    @Override
    public Dvd getDvdById(Integer id) throws DvdStoreException {
        DvdCollectionDAO dvdCollectionDAO = new DvdCollectionDAOImpl();
        // Returns null if there is no dvd matching id given by user 
        return dvdCollectionDAO.getDvdById(id);
    }

    @Override
    public Period calculateElapsedTime(Dvd dvd) throws DvdStoreException {
        DvdCollectionDAO dvdCollectionDAO = new DvdCollectionDAOImpl();
        LocalDate currentDate = LocalDate.now();
        LocalDate releaseDate = dvd.getReleaseDate();
        Period period;
        
        try {
            period = DateHelper.calculateDateDifference(releaseDate, currentDate);
        } catch(DateTimeParseException e) {
            throw new DvdStoreException(DATE_EXCEPTION, e);    
        }
        return period;
    }

    @Override
    public Genre getGenreById(Integer id) throws DvdStoreException {
        GenreCatalogueDAO genreCatalogueDAO = new GenreCatalogueDAOImpl();
        return genreCatalogueDAO.getGenreById(id);
    }

    @Override
    public Dvd modifyDvdDetails (Integer id, Dvd dvd) 
            throws DvdStoreException {
        DvdCollectionDAO dvdCollectionDAO = new DvdCollectionDAOImpl();
        return dvdCollectionDAO.updateDvdById(id, dvd);
    }

    @Override
    public boolean removeDvdById(Integer id) throws DvdStoreException {
        DvdCollectionDAO dvdCollectionDAO = new DvdCollectionDAOImpl();
        return dvdCollectionDAO.deleteDvdById(id);     
    }

    @Override
    public boolean recoverDvd(Integer id) throws DvdStoreException {
        DvdCollectionDAO dvdCollectionDAO = new DvdCollectionDAOImpl();
        return dvdCollectionDAO.recoverDvd(id);     
    }

    @Override
    public List<Dvd> retrieveDvdCollection() throws DvdStoreException {
        DvdCollectionDAO dvdCollectionDAO = new DvdCollectionDAOImpl();
        return dvdCollectionDAO.getDvdCollection();
    }

    @Override
    public List<Genre> retrieveGenreCatalogue() throws DvdStoreException {
        GenreCatalogueServiceImpl genreCatalogueService = 
            new GenreCatalogueServiceImpl();
        return genreCatalogueService.retrieveGenreCatalogue();
    }


}
