package com.ideas2it.dvdstore.service.impl;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.dvdstore.dao.CustomerDAO;
import com.ideas2it.dvdstore.dao.DvdCollectionDAO;
import com.ideas2it.dvdstore.dao.PurchaseOrderDAO;
import com.ideas2it.dvdstore.dao.impl.DvdCollectionDAOImpl;
import com.ideas2it.dvdstore.dao.impl.CustomerDAOImpl;
import com.ideas2it.dvdstore.dao.impl.PurchaseOrderDAOImpl;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.LineItem;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.service.DvdCollectionService;
import com.ideas2it.dvdstore.service.LineItemService;
import com.ideas2it.dvdstore.service.PurchaseOrderService;
import com.ideas2it.dvdstore.service.UserService;
import com.ideas2it.dvdstore.service.impl.DvdCollectionServiceImpl;
import com.ideas2it.dvdstore.service.impl.LineItemServiceImpl;
import com.ideas2it.dvdstore.service.impl.PurchaseOrderServiceImpl;
import com.ideas2it.dvdstore.service.impl.UserServiceImpl;
import com.ideas2it.dvdstore.utils.DateHelper;

import static com.ideas2it.dvdstore.common.Constants.*;

/**
 * Calculates the elapsed time between today's date and the release date 
 * of the dvd
 *
 * @author Ganesh Venkat S 
 */

public class CustomerServiceImpl implements CustomerService {

    @Override
    public Customer addCustomer(Customer customer) throws DvdStoreException {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.addCustomer(customer);
    }

    @Override
    public Customer getCustomerById(Integer id) throws DvdStoreException {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.getCustomerById(id);
    }

    @Override
    public Integer getCustomerIdByUserEmailId(String emailId) throws DvdStoreException {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.getCustomerIdByUserEmailId(emailId);
    }

    @Override
    public Customer updateCustomerDetails(Customer customer) throws DvdStoreException {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.updateCustomerDetails(customer);
    }

    @Override
    public Dvd updateDvdDetails(Integer id, Dvd updatedDvd) throws DvdStoreException {
        DvdCollectionService dvdCollectionService = 
            new DvdCollectionServiceImpl();        
        // Returns null if there is no dvd matching id given by user 
        return dvdCollectionService.modifyDvdDetails(id, updatedDvd);
    }

    @Override
    public boolean removeCustomerById(Integer id) throws DvdStoreException {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.deleteCustomerById(id);     
    }

    @Override
    public List<Customer> retrieveCustomers() throws DvdStoreException {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.getCustomers();
    }

    @Override
    public Dvd getDvdById(Integer id) throws DvdStoreException {
        DvdCollectionService dvdCollectionService = 
            new DvdCollectionServiceImpl();        
        // Returns null if there is no dvd matching id given by user 
        return dvdCollectionService.getDvdById(id);
    }

    @Override
    public User addUser(User user) throws DvdStoreException {        
        UserService userService = new UserServiceImpl();
        return userService.addUser(user);
    }

    @Override
    public LineItem addLineItem(LineItem lineItem) throws DvdStoreException {        
        LineItemService lineItemService = new LineItemServiceImpl();
        return lineItemService.addLineItem(lineItem);
    }

    @Override
    public PurchaseOrder addCustomerOrder(PurchaseOrder purchaseOrder) throws DvdStoreException {        
        PurchaseOrderService purchaseOrderService = new PurchaseOrderServiceImpl();
        return purchaseOrderService.addPurchaseOrder(purchaseOrder);
    }

    @Override
    public boolean cancelCustomerOrder(Integer customerId, Integer orderId) throws DvdStoreException {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        return customerDAO.cancelCustomerOrder(customerId, orderId);
    }

    @Override
    public List<PurchaseOrder> getCustomerOrders(Integer customerId) throws DvdStoreException {        
        PurchaseOrderService purchaseOrderService = new PurchaseOrderServiceImpl();
        return purchaseOrderService.getPurchaseOrders(customerId);
    }

}
