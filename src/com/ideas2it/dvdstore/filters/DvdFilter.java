package com.ideas2it.dvdstore.filters;

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

import static com.ideas2it.dvdstore.common.Constants.*; 


public class DvdFilter implements Filter {

    private ServletContext context;
    
    public void init(FilterConfig arg0) throws ServletException {
   
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String loginURI = req.getContextPath() + "/index.jsp";

        // System.out.println(req.getParameter("email-id"));

        boolean loggedIn = session != null && session.getAttribute("emailId") != null;
        boolean loginRequest = req.getRequestURI().equals(loginURI);
        boolean authenticateRequest = req.getRequestURI().endsWith("usercontroller") && req.getParameter("email-id") != null;

        if (loggedIn || loginRequest || authenticateRequest) {
            // HTTP 1.1
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            // HTTP 1.0
            res.setHeader("Pragma", "no-cache"); 
            res.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(loginURI);
        }
    }   

    public void destroy() {

    }
}
