package com.ideas2it.dvdstore.service.impl;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.dvdstore.dao.CustomerDAO;
import com.ideas2it.dvdstore.dao.UserDAO;
import com.ideas2it.dvdstore.dao.PurchaseOrderDAO;
import com.ideas2it.dvdstore.dao.impl.CustomerDAOImpl;
import com.ideas2it.dvdstore.dao.impl.UserDAOImpl;
import com.ideas2it.dvdstore.dao.impl.PurchaseOrderDAOImpl;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.service.DvdCollectionService;
import com.ideas2it.dvdstore.service.UserService;
import com.ideas2it.dvdstore.service.PurchaseOrderService;
import com.ideas2it.dvdstore.service.impl.CustomerServiceImpl;
import com.ideas2it.dvdstore.service.impl.LineItemServiceImpl;
import com.ideas2it.dvdstore.service.impl.PurchaseOrderServiceImpl;
import com.ideas2it.dvdstore.utils.DateHelper;

import static com.ideas2it.dvdstore.common.Constants.*;

/**
 * User is associated with an email, password and role: Customer or Admin 
 *
 * @author Ganesh Venkat S 
 */

public class UserServiceImpl implements UserService {

    @Override
    public User addUser(User user) throws DvdStoreException {        
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.addUser(user);
    }

    @Override
    public User authenticateUser(String email, String password) throws DvdStoreException {        
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.authenticateUser(email, password);
    }

    @Override
    public Integer getCustomerIdByUserEmailId(String emailId) throws DvdStoreException {
        CustomerService customerService = new CustomerServiceImpl();
        return customerService.getCustomerIdByUserEmailId(emailId);
    }

}
