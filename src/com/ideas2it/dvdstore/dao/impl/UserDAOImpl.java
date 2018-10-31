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
import com.ideas2it.dvdstore.dao.UserDAO;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.logger.DvdStoreLogger;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.sessionfactory.SessionsFactory;

public class UserDAOImpl implements UserDAO { 

    DvdStoreLogger logger = new DvdStoreLogger();

    @Override
    public User addUser(User user) throws DvdStoreException {

        Transaction tx = null;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("User not added" + ":\t" + e);
            throw new DvdStoreException("User not added");
        } 
        return user;
    }

    @Override
    public User authenticateUser(String emailId, String password) throws DvdStoreException {

        Transaction tx = null;
        User user = new User();
        List<User> users = new ArrayList<User>();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> userRoot = criteria.from(User.class);
            Predicate[] predicates = new Predicate[2];
            predicates[0] = builder.equal(userRoot.get("emailId"), emailId);
            predicates[1] = builder.equal(userRoot.get("password"), password);
            criteria.select(userRoot).where(predicates);
            // returns null if invalid user
            users = session.createQuery(criteria).getResultList();
            for (User auser: users) {
                user = auser;
            }
            if(users.isEmpty()) {
                user = null;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("User not authenticated" + ":\t" + e);
            throw new DvdStoreException("User not authenticated");
        } 
        return user;
    }

}

