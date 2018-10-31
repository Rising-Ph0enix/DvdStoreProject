package com.ideas2it.dvdstore.dao.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.cfg.Configuration;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ideas2it.dvdstore.dao.DvdCollectionDAO;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.logger.DvdStoreLogger;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.sessionfactory.SessionsFactory;

import static com.ideas2it.dvdstore.common.Constants.*;

/**
 * Performs basic CRUD operations on the Dvd Collection:
 * Add new Dvd; Search, Modify, Update and Delete Dvd by id
 *
 * @author Ganesh Venkat S
 */

public class DvdCollectionDAOImpl implements DvdCollectionDAO { 
    DvdStoreLogger logger = new DvdStoreLogger();

    @Override
    public boolean isDuplicate(Dvd dvd) throws DvdStoreException {
        Transaction tx = null;
        boolean isDuplicated = false;
        List<Dvd> dvds = new ArrayList<Dvd>();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Dvd> criteria = builder.createQuery(Dvd.class);
            Root<Dvd> dvdRoot = criteria.from(Dvd.class);
            Predicate[] predicates = new Predicate[3];
            predicates[0] = builder.equal(dvdRoot.get("movieName"), dvd.getMovieName());
            predicates[1] = builder.equal(dvdRoot.get("language"), dvd.getLanguage());
            predicates[2] = builder.equal(dvdRoot.get("releaseDate"), dvd.getReleaseDate());
            criteria.select(dvdRoot).where(predicates);
            dvds = session.createQuery(criteria).getResultList();
            if (!dvds.isEmpty()) {
                isDuplicated = true;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Duplicate could not be checked" + ":\t" + dvd.getId() + "\t" + e);
            throw new DvdStoreException("Duplicate could not be checked");
        }
        return isDuplicated;
    } 

    @Override
    public Dvd addDvd(Dvd dvd) throws DvdStoreException {
        Transaction tx = null;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(dvd);
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(DVD_NOT_ADDED + ":\t" + dvd.getMovieName() + "\t" + e);
            throw new DvdStoreException(DVD_NOT_ADDED);
        } 

        return dvd;
    }

    @Override
    public List<Dvd> getDvdByName(String movieName) throws DvdStoreException {
        Transaction tx = null;
        List<Dvd> dvds = new ArrayList<Dvd>();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Dvd> criteria = builder.createQuery(Dvd.class);
            Root<Dvd> dvdRoot = criteria.from(Dvd.class);
            criteria.select(dvdRoot).where(builder.equal(dvdRoot.get("movieName"), movieName));
            dvds = session.createQuery(criteria).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(DVD_NOT_SEARCHED + ":\t" + movieName + "\t" + e);
            throw new DvdStoreException(DVD_NOT_SEARCHED);
        }
        // Returns an empty list if there is no dvd whose id matches id given by user
        return dvds;
    }

    @Override
    public Dvd getDvdById(Integer id) throws DvdStoreException {
        Transaction tx = null;
        Dvd dvd = new Dvd();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Dvd> criteria = builder.createQuery(Dvd.class);
            Root<Dvd> dvdRoot = criteria.from(Dvd.class);
            criteria.select(dvdRoot).where(builder.equal(dvdRoot.get("id"), id));
            dvd = session.createQuery(criteria).getSingleResult();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(DVD_NOT_SEARCHED + ":\t" + id + "\t" + e);
            throw new DvdStoreException(DVD_NOT_SEARCHED);
        }
        // Returns an empty list if there is no dvd whose id matches id given by user
        return dvd;
    }

    @Override
    public Dvd updateDvdById(Integer id, Dvd updatedDvd) throws DvdStoreException {
        Transaction tx = null;
        Dvd dvd;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) { 
            tx = session.beginTransaction();
            
            // Retrieving object which we want to update - dvd object is not persistent here I suspect?
            //session.merge
            dvd = (Dvd) session.get(Dvd.class, id);
            // Set the updated dvd details to the model field 
            dvd.setMovieName(updatedDvd.getMovieName());
            dvd.setReleaseDate(updatedDvd.getReleaseDate());
            dvd.setLanguage(updatedDvd.getLanguage());
            dvd.setPrice(updatedDvd.getPrice());
            dvd.setQuantity(updatedDvd.getQuantity());
            dvd.setGenres(updatedDvd.getGenres());
            session.update(dvd);
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(DVD_NOT_UPDATED + ":\t" + id + "\t" + e);
            throw new DvdStoreException(DVD_NOT_UPDATED);
        }            
        return dvd;
    }

    @Override
    public boolean deleteDvdById(Integer id) throws DvdStoreException {
        Transaction tx = null;
        boolean isDeleted = false;
        SessionsFactory factory = SessionsFactory.getInstance();
        Dvd dvd;
         
        try (Session session = factory.getSessionFactory().openSession()){   
            tx = session.beginTransaction();
            dvd = (Dvd) session.get(Dvd.class, id);
            session.delete(dvd);
            tx.commit();
            isDeleted = true;
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            } 
            logger.error(DVD_NOT_REMOVED + ":\t" + id + "\t" + e);
            throw new DvdStoreException(DVD_NOT_REMOVED);
        }
        return isDeleted;
    }

    @Override
    public boolean recoverDvd(Integer id) throws DvdStoreException {
        Transaction tx = null;
        Dvd dvd;
        boolean isRecovered = false;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query query = session.getNamedQuery("recoverDvd").setParameter(1, id);
            query.executeUpdate();

            isRecovered = true;
            tx.commit(); 
        } catch(HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(DVD_NOT_RECOVERED + ":\t" + e);
            throw new DvdStoreException(DVD_NOT_RECOVERED);
        } 
        return isRecovered;
    }

    @Override
    public List<Dvd> getDvdCollection() throws DvdStoreException {
        Transaction tx = null;
        List<Dvd> dvdCollection = new ArrayList<Dvd>();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Dvd> criteria = builder.createQuery(Dvd.class);
            Root<Dvd> dvdRoot = criteria.from(Dvd.class);
            dvdCollection = session.createQuery(criteria).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(DVD_NOT_DISPLAYED + ":\t" + e);
            throw new DvdStoreException(DVD_NOT_DISPLAYED);
        }
        // Returns an empty list if there are no dvds
        return dvdCollection;
    }


}













