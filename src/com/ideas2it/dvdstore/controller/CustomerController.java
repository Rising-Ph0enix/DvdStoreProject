package com.ideas2it.dvdstore.controller;

import java.io.*;
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
import javax.servlet.*;
import javax.servlet.http.*;

import com.ideas2it.dvdstore.exception.DvdStoreException;
import com.ideas2it.dvdstore.logger.DvdStoreLogger;
import com.ideas2it.dvdstore.model.Address;
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.model.LineItem;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.service.DvdCollectionService;
import com.ideas2it.dvdstore.service.GenreCatalogueService;
import com.ideas2it.dvdstore.service.PurchaseOrderService;
import com.ideas2it.dvdstore.service.impl.CustomerServiceImpl;
import com.ideas2it.dvdstore.service.impl.DvdCollectionServiceImpl;
import com.ideas2it.dvdstore.service.impl.GenreCatalogueServiceImpl;
import com.ideas2it.dvdstore.service.impl.PurchaseOrderServiceImpl;
import com.ideas2it.dvdstore.utils.InputValidator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  
import org.springframework.ui.ModelMap; 
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.servlet.ModelAndView;  

import static com.ideas2it.dvdstore.common.Constants.*; 


@Controller 
@RequestMapping("customercontroller") 
public class CustomerController {

    DvdStoreLogger logger = new DvdStoreLogger();

    @RequestMapping(value="menu-add-customer", method=RequestMethod.GET)
    public String displayAddCustomerForm(Model model) {  
        model.addAttribute("customer", new Customer());   
        return "add-customer";  
    }

    @RequestMapping(value="add-customer-submit", method=RequestMethod.POST)
    public String addCustomer(@ModelAttribute("customer") Customer customer, BindingResult result, HttpServletRequest request) {  
        
        try {
            CustomerService customerService = 
                new CustomerServiceImpl();

            customer = customerService.addCustomer(customer); 

            // Note in the customer dao - it adds a Plain customer - with user & address - but No purchase orders

            // out.println(CUSTOMER_ADDED + customer.getName());
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_ADDED + e);
            // out.println(CUSTOMER_NOT_ADDED + customer.getName());            
        }

        return "dvd-added";
    }

    @RequestMapping(value="search-customer-submit", method=RequestMethod.GET)
    public ModelAndView searchCustomer(HttpServletRequest request) {   
        Integer customerId = null;       
        Customer customer = new Customer();
        //List<Genre> dvdGenres = new ArrayList<Genre>();

        try {
            CustomerService customerService = 
                new CustomerServiceImpl();

            // id = Integer.parseInt(request.getParameter("customer-id"));
            HttpSession session = request.getSession();
            customerId = (Integer) session.getAttribute("customerId");
            customer = customerService.getCustomerById(customerId);

        } catch (NumberFormatException e) {
            logger.error(CUSTOMER_NOT_SEARCHED + e);
            // out.println(CUSTOMER_NOT_SEARCHED + id);
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_SEARCHED + e);
            // out.println(CUSTOMER_NOT_SEARCHED + id);            
        }

        return new ModelAndView("search-customer", "customer", customer);  
    }
 
    @RequestMapping(value="menu-modify-customer", method=RequestMethod.GET)
    public String displayModifyCustomerForm(Model model, HttpServletRequest request) { 
        Customer customer = new Customer(); 
        Integer customerId = null;
        try {
            CustomerService customerService = 
                new CustomerServiceImpl();

            HttpSession session = request.getSession();
            customerId = (Integer) session.getAttribute("customerId");
            customer = customerService.getCustomerById(customerId);
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_UPDATED + e);
        } 

        model.addAttribute("customer", customer);  
        return "modify-customer";
    }

    @RequestMapping(value="modify-customer-submit", method=RequestMethod.POST)
    public String modifyCustomer(@ModelAttribute("customer")Customer customer, BindingResult result, HttpServletRequest request) {  
  
        Integer id = 0;
        boolean isModified = false;

        try {
            CustomerService customerService = 
                new CustomerServiceImpl();
            Customer existingCustomer = customerService.getCustomerById(customer.getId());
            customer.setUser(existingCustomer.getUser());
            customer = customerService.updateCustomerDetails(customer);
            // out.println(CUSTOMER_UPDATED + id);
        } catch (NumberFormatException e) {
            logger.error(CUSTOMER_NOT_UPDATED + e);
            // out.println(CUSTOMER_NOT_UPDATED + id);
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_UPDATED + e);
            // out.println(CUSTOMER_NOT_UPDATED + id);            
        }
        return "dvd-added";
    }     

    // Don't need Spring form for this
    @RequestMapping(value="menu-add-order", method=RequestMethod.GET)
    public ModelAndView displayAddOrderForm(Model model) {   
        Dvd dvd = new Dvd();
        PurchaseOrder purchaseOrder;
        LineItem lineItem;
        ModelAndView modelAndView = new ModelAndView();
        List<Dvd> dvdCollection = new ArrayList<Dvd>();
        List<Genre> genreCatalogue = new ArrayList<Genre>();
        DvdCollectionService dvdCollectionService = 
            new DvdCollectionServiceImpl();

        try {
            dvdCollection = dvdCollectionService.retrieveDvdCollection();
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_UPDATED + e);
            // System.out.println(e.getMessage());
        } 
        model.addAttribute("dvd", dvd);

        return new ModelAndView("add-order", "dvdCollection", dvdCollection);  
    }

    // Not using the model attribute here
    @RequestMapping(value="add-order-submit", method=RequestMethod.POST)
    public String addOrder(@ModelAttribute("dvd")Dvd dvd, BindingResult result, HttpServletRequest request) {  
        Integer customerId = null;
        Customer customer = new Customer();
        CustomerService customerService = 
            new CustomerServiceImpl();
        	
        try {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            List<Dvd> orderedDvds = new ArrayList();

            // set the date
            purchaseOrder.setOrderedDate(LocalDate.now());

            // set the customer associated with purchaseOrder
            HttpSession session = request.getSession();
            customerId = (Integer) session.getAttribute("customerId");
            //System.out.println(customerId);
            customer = customerService.getCustomerById(customerId);
            purchaseOrder.setCustomer(customer);

            purchaseOrder = customerService.addCustomerOrder(purchaseOrder); 

            // Get the selected dvds
            String selectedDvdsIds[] = request.getParameterValues("selected-dvds");
            for (String dvdIdString: selectedDvdsIds) {
                Integer dvdId = Integer.parseInt(dvdIdString);
                Dvd aDvd = customerService.getDvdById(dvdId);  
                orderedDvds.add(aDvd);
            } 

            List<Integer> orderedDvdQuantities = new ArrayList<Integer>();

            String orderedDvdQuants[] = request.getParameterValues("dvd-quantities");

            for (String orderedDvdQuantityString: orderedDvdQuants) {
                System.out.println(orderedDvdQuantityString);
                Integer orderedDvdQuantity = null;
                if(orderedDvdQuantityString != null && !orderedDvdQuantityString.isEmpty()) {
                    orderedDvdQuantity = Integer.parseInt(orderedDvdQuantityString);
                }
                // redundant check?
                if(orderedDvdQuantity != null) { 
                    orderedDvdQuantities.add(orderedDvdQuantity);
                }
            } 

            for (Dvd orderedDvd: orderedDvds) {
                int i = 0;
                LineItem lineItem = new LineItem();
                lineItem.setPrice(orderedDvd.getPrice());
                lineItem.setQuantity(orderedDvdQuantities.get(i));
                lineItem.setPurchaseOrder(purchaseOrder); 
                lineItem.setDvd(orderedDvd);
                lineItem = customerService.addLineItem(lineItem);
                orderedDvd.setQuantity(orderedDvd.getQuantity() - orderedDvdQuantities.get(i));
                dvd = customerService.updateDvdDetails(orderedDvd.getId(), orderedDvd);
                i++;
            }

        } catch (DvdStoreException e) {
            logger.error(ORDER_NOT_UPDATED + e);
            // System.out.println("Purchase Order added" + ":\t" + purchaseOrder.getId());
            //out.println(e.getMessage() + purchaseOrder.getId());
        }

        return "dvd-added";
    }  

    @RequestMapping(value="search-orders-submit", method=RequestMethod.GET)
    public ModelAndView displayCustomerOrders(HttpServletRequest request) {   
        Integer customerId = null;       
        Customer customer = new Customer();
        // PurchaseOrder purchaseOrder = new PurchaseOrder();
        List<PurchaseOrder> purchaseOrders = new ArrayList<PurchaseOrder>();
        ModelAndView modelAndView = new ModelAndView();

        try {
            CustomerService customerService = 
                new CustomerServiceImpl();

            HttpSession session = request.getSession();
            customerId = (Integer) session.getAttribute("customerId");
            customer = customerService.getCustomerById(customerId);

            // Get the purchase orders by this customer
            purchaseOrders = customerService.getCustomerOrders(customerId);
        } catch (NumberFormatException e) {
            logger.error(CUSTOMER_NOT_SEARCHED + e);
            // out.println(CUSTOMER_NOT_SEARCHED + id);
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_SEARCHED + e);
            // out.println(CUSTOMER_NOT_SEARCHED + id);            
        }

        modelAndView.setViewName("search-customer-orders");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("purchaseOrders", purchaseOrders);

        return modelAndView;  
    }


}




/* 

    @RequestMapping(value="menu-add-order", method=RequestMethod.GET)
    public ModelAndView displayAddOrderForm(Model model) {   
        Dvd dvd;
        List<Dvd> dvdCollection = new ArrayList<Dvd>();
        DvdCollectionService dvdCollectionService = 
            new DvdCollectionServiceImpl();

        try {
            dvdCollection = dvdCollectionService.retrieveDvdCollection();
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage());
        } 
        model.addAttribute("dvd", new Dvd());
        return new ModelAndView("add-order", "dvdCollection", dvdCollection);  
    }

    @RequestMapping(value="add-order-submit", method=RequestMethod.POST)
    public String addDvd(@ModelAttribute("dvd")Dvd dvd, BindingResult result, HttpServletRequest request) {  
  
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

        return "dvd-added";
    }  

 */






/* 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        response.setContentType("text/html");
	PrintWriter out = response.getWriter();

        String userChoice = request.getParameter("admincustomermenu-button");

        switch(userChoice) {
            case "menu-search-customer":
                displaySearchCustomerForm(request, response);
                break;
            case "search-customer-submit":
                searchCustomer(request, response, out);
                break;
            case "menu-display-customers":
                displayAllCustomers(request, response);
                break;
            // case "menu-search-customer-account":
            //    displayAllCustomers(request, response);
            //    break; 
            case "menu-purchase-order":
                displayPurchaseOrderForm(request, response);
                break;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        response.setContentType("text/html");
	PrintWriter out = response.getWriter();

        String userChoice = request.getParameter("add-customer-menu-button");

        switch(userChoice) {
            case "add-customer-submit":
                addCustomer(request, response, out);
                break;
        }
    }

    public void addCustomer(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        Customer customer = new Customer();
        User user = new User();
        Address address = new Address();
        
        try {
            CustomerService customerService = 
                new CustomerServiceImpl();

            customer.setName(request.getParameter("name"));
            customer.setPhoneNumber(request.getParameter("phone-number"));
            
            String emailId = request.getParameter("email-id");
            String password = request.getParameter("password");
            user.setEmailId(emailId);
            user.setPassword(password);
            user.setRole("Customer");
            customer.setUser(user);
        
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            Integer pincode = Integer.parseInt(request.getParameter("pincode"));
            address.setStreet(street);
            address.setCity(city);
            address.setState(state);
            address.setPinCode(pincode);
            customer.setAddress(address);

            customer = customerService.addCustomer(customer); 
            user.setCustomer(customer);
            user = customerService.addUser(user);
            // address.setCustomer(customer);
            // address = customerService.addCustomerAddress(address); 

            out.println(CUSTOMER_ADDED + customer.getName());
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_ADDED + e);
            out.println(CUSTOMER_NOT_ADDED + customer.getName());            
        }
    }

    public void displayPurchaseOrderForm(HttpServletRequest request, HttpServletResponse response) {
        dispatchRequest("purchase-order.jsp", request, response);
    }

    public void displaySearchCustomerForm(HttpServletRequest request, HttpServletResponse response) {
        dispatchRequest("search-customer.jsp", request, response);
    }

    public void searchCustomer(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        Integer id = null;       
        Customer customer = new Customer();
        //List<Genre> dvdGenres = new ArrayList<Genre>();

        try {
            CustomerService customerService = 
                new CustomerServiceImpl();
            id = Integer.parseInt(request.getParameter("customer-id"));
            customer = customerService.getCustomerById(id); 
            // dvdGenres = dvd.getGenres();
            // out.println(DVD_SEARCHED);
        } catch (NumberFormatException e) {
            logger.error(CUSTOMER_NOT_SEARCHED + e);
            out.println(CUSTOMER_NOT_SEARCHED + id);
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_SEARCHED + e);
            out.println(CUSTOMER_NOT_SEARCHED + id);            
        }

        request.setAttribute("customer", customer);
        dispatchRequest("search-customer.jsp", request, response);
    }

    public void displayAllCustomers(HttpServletRequest request, HttpServletResponse response) {
        List<Customer> customerAccounts = new ArrayList<Customer>();

        try {
            CustomerService customerService = 
                new CustomerServiceImpl();
            customerAccounts = customerService.retrieveCustomers();
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage());
        } 
        request.setAttribute("customerAccounts", customerAccounts);
        dispatchRequest("display-all-customers.jsp", request, response);
    }

    public void dispatchRequest(String nextPage, HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            logger.error("Servlet Exception: Request could not be forwarded" + e);
        } catch (IOException e) {
            logger.error("IO Exception: Request could not be forwarded" + e);
        } 
    }    
*/





