package com.ideas2it.dvdstore.service.impl;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.dvdstore.dao.CustomerDAO;
import com.ideas2it.dvdstore.dao.LineItemDAO;
import com.ideas2it.dvdstore.dao.PurchaseOrderDAO;
import com.ideas2it.dvdstore.dao.impl.CustomerDAOImpl;
import com.ideas2it.dvdstore.dao.impl.LineItemDAOImpl;
import com.ideas2it.dvdstore.dao.impl.PurchaseOrderDAOImpl;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.LineItem;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.service.DvdCollectionService;
import com.ideas2it.dvdstore.service.LineItemService;
import com.ideas2it.dvdstore.service.PurchaseOrderService;
import com.ideas2it.dvdstore.service.impl.LineItemServiceImpl;
import com.ideas2it.dvdstore.service.impl.PurchaseOrderServiceImpl;
import com.ideas2it.dvdstore.utils.DateHelper;

import static com.ideas2it.dvdstore.common.Constants.*;

/**
 * Calculates the elapsed time between today's date and the release date 
 * of the dvd
 *
 * @author Ganesh Venkat S 
 */

public class LineItemServiceImpl implements LineItemService {

    @Override
    public LineItem addLineItem(LineItem lineItem) throws DvdStoreException {        
        LineItemDAO lineItemDAO = new LineItemDAOImpl();
        return lineItemDAO.addLineItem(lineItem);
    }

}
