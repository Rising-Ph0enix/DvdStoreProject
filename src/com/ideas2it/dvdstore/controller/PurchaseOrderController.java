package com.ideas2it.dvdstore.controller;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.logger.DvdStoreLogger;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.service.DvdCollectionService;
import com.ideas2it.dvdstore.service.PurchaseOrderService;
import com.ideas2it.dvdstore.service.impl.DvdCollectionServiceImpl;
import com.ideas2it.dvdstore.service.impl.GenreCatalogueServiceImpl;
import com.ideas2it.dvdstore.service.impl.PurchaseOrderServiceImpl;
import com.ideas2it.dvdstore.utils.InputValidator;

import static com.ideas2it.dvdstore.common.Constants.*; 

/** 
 * Gets the actions to be performed on the dvdCollection as input from the user 
 * and displays the corresponding output
 *
 * @author Ganesh Venkat S
 */
public class PurchaseOrderController {

    public void displayMenu() { 
        int userChoice = 0;
        Scanner userInput = new Scanner(System.in);
        System.out.println("1. Add a Purchase Order");
        System.out.println("2. Search for a Purchase Order");            
        System.out.println("3. Remove a Purchase Order");
        // Later - display purchase orders for this particular customer
        System.out.println("4. Display all purchase orders");
        try {
            userChoice = Integer.parseInt(userInput.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Choose a valid option from the menu");
        }
        switch (userChoice) {
            case 1:
                addPurchaseOrder();                    
                break;
            case 2:
                searchPurchaseOrder();
                break;
            case 3:
                removePurchaseOrder();
                break;
            case 4:
                displayPurchaseOrders();
                break;
            default: 
                System.out.println(MENU_VALID_CHOICE);
        }
    }

    public void addPurchaseOrder() {
        PurchaseOrder purchaseOrder;
        PurchaseOrderService purchaseOrderService = 
            new PurchaseOrderServiceImpl();
        purchaseOrder = getPurchaseOrderDetails(new PurchaseOrder());	
        try {
            purchaseOrder = purchaseOrderService.addPurchaseOrder(purchaseOrder); 
            System.out.println("Purchase Order added" + ":\t" + purchaseOrder.getId());
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage() + purchaseOrder.getId());
        }
    } 

    public void searchPurchaseOrder() {
        Integer id;
        Period period = null;
        Scanner scanner = new Scanner(System.in);
        PurchaseOrderService purchaseOrderService = 
            new PurchaseOrderServiceImpl();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        System.out.println(PROMPT_DVD_SEARCH);
        id = Integer.parseInt(scanner.nextLine());
        try {
            purchaseOrder = purchaseOrderService.getPurchaseOrderById(id);
            System.out.println("Purchase Order Details\n: " + purchaseOrder);
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage() + id); 
        }
    }

    public void removePurchaseOrder() {
        Integer id = -1;
        boolean isDeleted;
        PurchaseOrder purchaseOrder;
        Scanner scanner = new Scanner(System.in);
        PurchaseOrderService purchaseOrderService = 
            new PurchaseOrderServiceImpl();

        System.out.println(PROMPT_DVD_REMOVE);

        try {
            id = Integer.parseInt(scanner.nextLine());
            isDeleted = purchaseOrderService.removePurchaseOrderById(id); 
            System.out.println("PurchaseOrder could not be removed" + ":\t" + id); 
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage() + id);
        }
    }

    public void displayPurchaseOrders() {

        try {
            PurchaseOrderService purchaseOrderService = 
                new PurchaseOrderServiceImpl();
            List<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();
            // Need separate methods - getAllPurchaseOrders, getPurchaseOrdersByCustomerId
            // 5 is a dummy parameter intended to remove compiler error
            purchaseOrders = purchaseOrderService.getPurchaseOrders(5);
            System.out.println(purchaseOrders);
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage());
        } 
    }

    // Need to work out Customer to get this right
    public PurchaseOrder getPurchaseOrderDetails(PurchaseOrder purchaseOrder) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("");
        // genre.setName(getName(scanner));
        
        return purchaseOrder;
    }

}
