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
import com.ideas2it.dvdstore.dao.PurchaseOrderDAO;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.logger.DvdStoreLogger;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.sessionfactory.SessionsFactory;

public class PurchaseOrderDAOImpl implements PurchaseOrderDAO {
    DvdStoreLogger logger = new DvdStoreLogger();

    @Override
    public PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder) throws DvdStoreException {
        
        /*
        User user = new User("tommy", "ymmot", "tommy@gmail.com");
        Group group = new Group("Coders");
        PurchaseOrder purchaseOrder = new PurchaseOrder("5", "24/06/1995");
        Dvd dvd = new Dvd("3", "Test", );
        */

        Transaction tx = null;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(purchaseOrder);
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Purchase Order not added" + ":\t" + e);
            throw new DvdStoreException("Purchase Order not added");
        } 
        return purchaseOrder;
    }


    @Override
    public PurchaseOrder getPurchaseOrderById(Integer id) throws DvdStoreException {
        Transaction tx = null;
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PurchaseOrder> criteria = builder.createQuery(PurchaseOrder.class);
            Root<PurchaseOrder> purchaseOrderRoot = criteria.from(PurchaseOrder.class);
            criteria.select(purchaseOrderRoot).where(builder.equal(purchaseOrderRoot.get("id"), id));
            purchaseOrder = session.createQuery(criteria).getSingleResult();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Purchase Order not searched" + ":\t" + id + "\t" + e);
            throw new DvdStoreException("Purchase Order not searched");
        }
        // Returns an empty list if there is no dvd whose id matches id given by user
        return purchaseOrder;
    }

    @Override
    public boolean deletePurchaseOrderById(Integer id) throws DvdStoreException {
        Transaction tx = null;
        boolean isDeleted = false;
        SessionsFactory factory = SessionsFactory.getInstance();
         
        try (Session session = factory.getSessionFactory().openSession()){   
            tx = session.beginTransaction();
            PurchaseOrder purchaseOrder = (PurchaseOrder) session.get(PurchaseOrder.class, id);
            session.delete(purchaseOrder);
            tx.commit();
            isDeleted = true;
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            } 
            logger.error("Purchase Order not deleted" + ":\t" + id + "\t" + e);
            throw new DvdStoreException("Purchase Order not deleted");
        }
        return isDeleted;
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrders(Integer customerId) throws DvdStoreException {
        Transaction tx = null;
        List<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PurchaseOrder> criteria = builder.createQuery(PurchaseOrder.class);
            Root<PurchaseOrder> purchaseOrderRoot = criteria.from(PurchaseOrder.class);
            // get customer_id or get customer
            criteria.select(purchaseOrderRoot).where(builder.equal(purchaseOrderRoot.get("customer"), customerId));

            purchaseOrders = session.createQuery(criteria).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Purchase Orders not displayed" + ":\t" + e);
            throw new DvdStoreException("Purchase Orders not displayed");
        }
        // Returns an empty list if there are no dvds
        return purchaseOrders;
    }



} 
