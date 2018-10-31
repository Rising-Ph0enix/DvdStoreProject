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
import com.ideas2it.dvdstore.model.Customer;
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.model.User;
import com.ideas2it.dvdstore.model.PurchaseOrder;
import com.ideas2it.dvdstore.service.CustomerService;
import com.ideas2it.dvdstore.service.DvdCollectionService;
import com.ideas2it.dvdstore.service.UserService;
import com.ideas2it.dvdstore.service.PurchaseOrderService;
import com.ideas2it.dvdstore.service.impl.CustomerServiceImpl;
import com.ideas2it.dvdstore.service.impl.DvdCollectionServiceImpl;
import com.ideas2it.dvdstore.service.impl.GenreCatalogueServiceImpl;
import com.ideas2it.dvdstore.service.impl.UserServiceImpl;
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
@RequestMapping("usercontroller") 
public class UserController {

    DvdStoreLogger logger = new DvdStoreLogger();

    @RequestMapping(value="sign-in-submit", method=RequestMethod.POST)
    public String authenticateUser(Model model, HttpServletRequest request) {  

        Integer id = null;       
        User user = new User();

        try {
            UserService userService = 
                new UserServiceImpl();

            String emailId = request.getParameter("email-id");
            String password = request.getParameter("password");
            Integer customerId = null;

            user = userService.authenticateUser(emailId, password);

            if (user !=  null) {
                if (user.getRole().equals("Admin")) {
                    // Admin menu
                    HttpSession session = request.getSession();
                    // Use the email as a session attribute to set instead of customerId after you set custId to null for admin
                    // customerId = customerService.getCustomerIdByUserEmailId(emailId);
                    session.setAttribute("emailId", emailId);
                    // return "forward:admin-menu"; 
                    return "admin-menu";
                }
                else {
                    // Customer menu
                    HttpSession session = request.getSession();
                    customerId = userService.getCustomerIdByUserEmailId(emailId);
                    session.setAttribute("emailId", emailId);
                    session.setAttribute("customerId", customerId);
                    // return "forward:customer-menu"; 
                    return "customer-menu"; 
                }
            } else {
                // out.println("Invalid user");
            }
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_SEARCHED + e);
            // out.println(CUSTOMER_NOT_SEARCHED + id);            
        }        
   
        return "dvd-added";  
    }

    @RequestMapping(value="sign-out", method=RequestMethod.POST)
    public String signOutUser(HttpServletRequest request, HttpServletResponse response) {
        // String contextPath = request.getContextPath();
        // System.out.println(contextPath);
        HttpSession session = request.getSession(false);
        session.invalidate();
        // redirect to index
        return "redirect:../index"; 
    }
}






























/* 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        response.setContentType("text/html");
	PrintWriter out = response.getWriter();

        String userChoice = request.getParameter("login-button");

        switch(userChoice) {
            case "sign-in":
                authenticateUser(request, response, out); 
                break;
            case "sign-up":
                displayAddCustomerForm(request,response);
                break;
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        response.setContentType("text/html");
	PrintWriter out = response.getWriter();

        String userChoice = request.getParameter("login-button");

        switch(userChoice) {
            case "sign-out":
                signOutUser(request,response);
                break;
        }
    }

    public void displayAddCustomerForm(HttpServletRequest request, HttpServletResponse response) {
        dispatchRequest("add-customer.jsp", request, response);
    }

    public void authenticateUser(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        Integer id = null;       
        User user = new User();

        try {
            UserService userService = 
                new UserServiceImpl();

            String emailId = request.getParameter("email-id");
            String password = request.getParameter("password");

            user = userService.authenticateUser(emailId, password);

            if (user !=  null) {
                if (user.getRole().equals("Admin")) {
                    // Admin menu
                    HttpSession session = request.getSession();
                    session.setAttribute("emailId", emailId);
                    dispatchRequest("admin-menu.jsp", request, response); 
                }
                else {
                    // Customer menu
                    HttpSession session = request.getSession();
                    session.setAttribute("emailId", emailId);
                    dispatchRequest("customer-menu.jsp", request, response); 
                }
            } else {
                out.println("Invalid user");
            }
        } catch (DvdStoreException e) {
            logger.error(CUSTOMER_NOT_SEARCHED + e);
            out.println(CUSTOMER_NOT_SEARCHED + id);            
        }
    }

    public void signOutUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        dispatchRequest("index.jsp", request, response); 
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





