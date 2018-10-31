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

import com.ideas2it.dvdstore.dao.GenreCatalogueDAO;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.logger.DvdStoreLogger;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.sessionfactory.SessionsFactory;

import static com.ideas2it.dvdstore.common.Constants.*;

/**
 * Performs basic CRUD operations on the Genre catalogue
 * Add new genre; Search, Modify, Update and Delete genre by id
 *
 * @author Ganesh Venkat S
 */

public class GenreCatalogueDAOImpl implements GenreCatalogueDAO {
    private DvdStoreLogger logger = new DvdStoreLogger();

    @Override
    public Genre addGenre(Genre genre) throws DvdStoreException {
        Transaction tx = null;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(genre);
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(GENRE_NOT_ADDED + ":\t" + genre.getName() + "\t" + e);
            throw new DvdStoreException(GENRE_NOT_ADDED);
        } 

        return genre;
    }

    @Override
    public Genre getGenreByName(String name) throws DvdStoreException {
        Transaction tx = null;
        Genre genre = new Genre();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
            Root<Genre> genreRoot = criteria.from(Genre.class);
            criteria.select(genreRoot).where(builder.equal(genreRoot.get("name"), name));
            // Use criteria to query with session to fetch all genres with given name
            genre = session.createQuery(criteria).getSingleResult();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(GENRE_NOT_SEARCHED + ":\t" + name + "\t" + e);
            throw new DvdStoreException(GENRE_NOT_SEARCHED);
        }
        // Returns an empty list if there is no genre whose id matches id given by user
        return genre;
    }

    @Override
    public Genre getGenreById(Integer id) throws DvdStoreException {
        Transaction tx = null;
        Genre genre;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
            Root<Genre> genreRoot = criteria.from(Genre.class);
            criteria.select(genreRoot).where(builder.equal(genreRoot.get("id"), id));
            genre = session.createQuery(criteria).getSingleResult();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(GENRE_NOT_SEARCHED + ":\t" + id + "\t" + e);
            throw new DvdStoreException(GENRE_NOT_SEARCHED);
        }
        // Returns an empty list if there is no genre whose id matches id given by user
        return genre;
    }

    @Override
    public Genre updateGenreById(Integer id, Genre updatedGenre) throws DvdStoreException {
        Transaction tx = null;
        Genre genre;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) { 
            tx = session.beginTransaction();
            genre = (Genre) session.get(Genre.class, id);
            genre.setName(updatedGenre.getName());
            session.update(genre);
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(GENRE_NOT_UPDATED + ":\t" + id + "\t" + e);
            throw new DvdStoreException(GENRE_NOT_UPDATED);
        }            
        return genre;
    }

    @Override
    public boolean deleteGenreById(Integer id) throws DvdStoreException {
        Transaction tx = null;
        boolean isDeleted = false;
        SessionsFactory factory = SessionsFactory.getInstance();
         
        try (Session session = factory.getSessionFactory().openSession()){   
            tx = session.beginTransaction();
            Genre genre = (Genre) session.get(Genre.class, id);
            session.delete(genre);
            tx.commit();
            isDeleted = true;
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            } 
            logger.error(GENRE_NOT_REMOVED + ":\t" + id + "\t" + e);
            throw new DvdStoreException(GENRE_NOT_REMOVED);
        }
        return isDeleted;
    }

    @Override
    public boolean recoverGenre(Integer id) throws DvdStoreException {
        Transaction tx = null;
        boolean isRecovered = false;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query query = session.getNamedQuery("recoverGenre").setParameter(1, id);
            query.executeUpdate();

            isRecovered = true;
            tx.commit(); 
        } catch(HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(GENRE_NOT_RECOVERED + ":\t" + e);
            throw new DvdStoreException(GENRE_NOT_RECOVERED);
        } 
        return isRecovered;
    }

    @Override
    public List<Genre> getGenreCatalogue() throws DvdStoreException {
        Transaction tx = null;
        List<Genre> genreCatalogue = new ArrayList<Genre>();
        SessionsFactory factory = SessionsFactory.getInstance();

        // This is a static query so, you should be using jpql instead of criteria builder! 
        // For the others with < 1 parameter, jpql is preferred

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Genre> criteria = builder.createQuery(Genre.class);
            Root<Genre> genreRoot = criteria.from(Genre.class);
            criteria.orderBy(builder.asc(genreRoot.get("id")));
            genreCatalogue = session.createQuery(criteria).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error(GENRE_NOT_DISPLAYED + ":\t" + e);
            throw new DvdStoreException(GENRE_NOT_DISPLAYED);
        }
        // Returns an empty list if there are no genres
        return genreCatalogue;
    }



} 













