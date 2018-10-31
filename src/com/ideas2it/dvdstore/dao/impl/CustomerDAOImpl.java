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
import com.ideas2it.dvdstore.dao.CustomerDAO;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.logger.DvdStoreLogger;
import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.sessionfactory.SessionsFactory;

public class CustomerDAOImpl implements CustomerDAO {

    DvdStoreLogger logger = new DvdStoreLogger();

    @Override
    public Customer addCustomer(Customer customer) throws DvdStoreException {

        Transaction tx = null;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Address address = customer.getAddress();
            address.setCustomer(customer);

            User user = customer.getUser();
            user.setCustomer(customer);

	    session.save(customer);

            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Customer not added" + ":\t" + e);
            throw new DvdStoreException("Customer not added");
        } 
        return customer;
    }

    @Override
    public Customer getCustomerById(Integer id) throws DvdStoreException {
        Transaction tx = null;
        Customer customer = new Customer();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
            Root<Customer> customerRoot = criteria.from(Customer.class);
            criteria.select(customerRoot).where(builder.equal(customerRoot.get("id"), id));
            customer = session.createQuery(criteria).getSingleResult();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Customer not searched" + ":\t" + id + "\t" + e);
            throw new DvdStoreException("Customer not searched");
        }
        // Returns an empty list if there is no dvd whose id matches id given by user
        return customer;
    }

    @Override
    public Integer getCustomerIdByUserEmailId(String emailId) throws DvdStoreException {
        Transaction tx = null;
        // Customer customer = new Customer();
        User user = new User();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> userRoot = criteria.from(User.class);
            criteria.select(userRoot).where(builder.equal(userRoot.get("emailId"), emailId));
            user = session.createQuery(criteria).getSingleResult();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Customer not searched" + ":\t" + emailId + "\t" + e);
            throw new DvdStoreException("Customer not searched");
        }
        // Returns an empty list if there is no dvd whose id matches id given by user
        return user.getCustomer().getId();
    }

    @Override
    public PurchaseOrder getCustomerOrder(Integer customerId, Integer orderId) throws DvdStoreException {
        Transaction tx = null;
        PurchaseOrder purchaseOrder;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PurchaseOrder> criteria = builder.createQuery(PurchaseOrder.class);
            Root<PurchaseOrder> purchaseOrderRoot = criteria.from(PurchaseOrder.class);
            
            Predicate[] predicates = new Predicate[2];

            predicates[0] = builder.equal(purchaseOrderRoot.get("customer"), customerId);
            predicates[1] = builder.equal(purchaseOrderRoot.get("id"), orderId);
            criteria.select(purchaseOrderRoot).where(predicates);
            // customer can have more than one purchaseOrder
            purchaseOrder = session.createQuery(criteria).getSingleResult();

            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Purchase Order not searched" + ":\t" + customerId + "\t" + orderId + e);
            throw new DvdStoreException("Purchase Order not searched");
        }
        // Returns an empty list if there is no dvd whose id matches id given by user
        return purchaseOrder;
    }

    @Override
    public boolean cancelCustomerOrder(Integer customerId, Integer orderId) throws DvdStoreException {
        Transaction tx = null;
        boolean isDeleted = false;
        SessionsFactory factory = SessionsFactory.getInstance();
         
        try (Session session = factory.getSessionFactory().openSession()){   
            tx = session.beginTransaction();
            PurchaseOrder purchaseOrder = getCustomerOrder(customerId, orderId);
            session.delete(purchaseOrder);
            tx.commit();
            isDeleted = true;
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            } 
            logger.error("Purchase Order not deleted" + ":\t" + customerId + "\t" + orderId + "\t" + e);
            throw new DvdStoreException("Purchase Order not deleted");
        }
        return isDeleted;
    }

    @Override
    public Customer updateCustomerDetails(Customer customer) throws DvdStoreException {
        
        Transaction tx = null;
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Address address = customer.getAddress();
            address.setCustomer(customer);

            User user = customer.getUser();
            user.setCustomer(customer);
            
            session.update(customer);

            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Customer not updated" + ":\t" + e);
            throw new DvdStoreException("Customer not updated");
        } 
        return customer;
    }

    @Override
    public boolean deleteCustomerById(Integer id) throws DvdStoreException {
        Transaction tx = null;
        boolean isDeleted = false;
        SessionsFactory factory = SessionsFactory.getInstance();
         
        try (Session session = factory.getSessionFactory().openSession()){   
            tx = session.beginTransaction();
            Address address = (Address) session.get(Address.class, id);          
            Customer customer = (Customer) session.get(Customer.class, id);
            User user = (User) session.get(User.class, id);

            // delete address & user before deleting customer - Also have to delete purchaseOrder 

            session.delete(customer);
            tx.commit();
            isDeleted = true;
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            } 
            logger.error("Customer not deleted" + ":\t" + id + "\t" + e);
            throw new DvdStoreException("Customer not deleted");
        }
        return isDeleted;
    }

    @Override
    public List<Customer> getCustomers() throws DvdStoreException {
        Transaction tx = null;
        List<Customer> customers = new ArrayList<Customer>();
        SessionsFactory factory = SessionsFactory.getInstance();

        try (Session session = factory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
            Root<Customer> customerRoot = criteria.from(Customer.class);
            customers = session.createQuery(criteria).getResultList();
            tx.commit();
        } catch (HibernateException e) {
            if (null != tx) {
                tx.rollback();
            }  
            logger.error("Customers not displayed" + ":\t" + e);
            throw new DvdStoreException("Customers not displayed");
        }
        // Returns an empty list if there are no dvds
        return customers;
    }

}
