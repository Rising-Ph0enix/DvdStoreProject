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
import com.ideas2it.dvdstore.dao.LineItemDAO;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.logger.DvdStoreLogger;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.model.LineItem;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.sessionfactory.SessionsFactory;

public class LineItemDAOImpl implements LineItemDAO { 

    DvdStoreLogger logger = new DvdStoreLogger();

    @Override
    public LineItem addLineItem(LineItem lineItem) throws DvdStoreException {
       
        Transaction tx = null;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(lineItem);
            tx.commit();
        } catch (HibernateException e) {
            logger.error("LineItem not added" + ":\t" + e);
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("LineItem not added" + ":\t" + e);
            throw new DvdStoreException("LineItem not added");
        } 
        return lineItem;
    }




}





/*    @Override
    public List<LineItem> getLineItem(Integer id) throws DvdStoreException {
        Transaction tx = null;
        List<Dvd> dvds = new ArrayList<Dvd>();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Dvd> criteria = builder.createQuery(Dvd.class);
            Root<Dvd> dvdRoot = criteria.from(Dvd.class);
            criteria.select(dvdRoot).where(builder.equal(dvdRoot.get("id"), id));
            dvds = session.createQuery(criteria).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }  
            logger.error(DVD_NOT_SEARCHED + ":\t" + id + "\t" + e);
            throw new DvdStoreException(DVD_NOT_SEARCHED);
        }
        // Returns an empty list if there is no dvd whose id matches id given by user
        return dvds;
    } 
*/




