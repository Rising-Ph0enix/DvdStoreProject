package com.ideas2it.dvdstore.service.impl;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.ideas2it.dvdstore.dao.PurchaseOrderDAO;
import com.ideas2it.dvdstore.dao.impl.PurchaseOrderDAOImpl;
import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.service.DvdCollectionService;
import com.ideas2it.dvdstore.service.PurchaseOrderService;
import com.ideas2it.dvdstore.utils.DateHelper;

import static com.ideas2it.dvdstore.common.Constants.*;

/**
 * Customer can place many orders and many dvds can be ordered in a single order
 *
 * @author Ganesh Venkat S 
 */

public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Override
    public PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder) throws DvdStoreException {
        PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAOImpl();
        return purchaseOrderDAO.addPurchaseOrder(purchaseOrder);
    }

    @Override
    public PurchaseOrder getPurchaseOrderById(Integer id) throws DvdStoreException {
        PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAOImpl();
        return purchaseOrderDAO.getPurchaseOrderById(id);
    }

    @Override
    public boolean removePurchaseOrderById(Integer id) throws DvdStoreException {
        PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAOImpl();
        return purchaseOrderDAO.deletePurchaseOrderById(id);     
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrders(Integer customerId) throws DvdStoreException {
        PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAOImpl();
        return purchaseOrderDAO.getPurchaseOrders(customerId);
    }

}
