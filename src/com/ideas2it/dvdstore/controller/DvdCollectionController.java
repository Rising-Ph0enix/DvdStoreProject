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
import com.ideas2it.dvdstore.model.Dvd;
import com.ideas2it.dvdstore.model.Genre;
import com.ideas2it.dvdstore.service.DvdCollectionService;
import com.ideas2it.dvdstore.service.impl.DvdCollectionServiceImpl;
import com.ideas2it.dvdstore.service.impl.GenreCatalogueServiceImpl;
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
@RequestMapping("dvdcontroller") 
public class DvdCollectionController {

    DvdStoreLogger logger = new DvdStoreLogger();

    @RequestMapping(value="menu-add-dvd", method=RequestMethod.GET)
    public ModelAndView displayAddDvdForm(Model model) {  
  
        List<Genre> genreCatalogue = new ArrayList<Genre>();
            try {
                DvdCollectionService dvdCollectionService = 
                    new DvdCollectionServiceImpl();
                // Fetch the catalogue        
                genreCatalogue = dvdCollectionService.retrieveGenreCatalogue();
            } catch (DvdStoreException e) {
                System.out.println(e.getMessage());
            }
        model.addAttribute("dvd", new Dvd());   
        return new ModelAndView("add-dvd", "genreCatalogue", genreCatalogue);  
    }  

    @RequestMapping(value="add-dvd-submit", method=RequestMethod.POST)
    public String addDvd(@ModelAttribute("dvd")Dvd dvd, BindingResult result, HttpServletRequest request) {  
  
        DvdCollectionService dvdCollectionService = 
            new DvdCollectionServiceImpl();
        List<Genre> dvdGenres = new ArrayList();

        try {
       
            // Gets all the genre IDs as an array of Strings
            String selectedGenres[] = request.getParameterValues("genres");
            for(String genreIdString: selectedGenres) {
                Integer genreId = Integer.parseInt(genreIdString);
                Genre genre = dvdCollectionService.getGenreById(genreId);  
                dvdGenres.add(genre);
            }        
            dvd.setGenres(dvdGenres);
            // insert dvd
            dvd = dvdCollectionService.addDvd(dvd);
            // out.println(DVD_ADDED);
            // System.out.println(DVD_ADDED + ":\t" + dvd.getId()); - Context
        } catch(NumberFormatException e) {
            logger.error(DVD_NOT_ADDED + e);
            // out.println(DVD_NOT_SEARCHED + request.getParameter("movie-name"));
            //  System.out.println(DVD_NOT_SEARCHED + request.getParameter("movie-name"))
        } catch (DateTimeParseException e) {
            logger.error(e.getMessage());
            // out.println(ISO_FORMAT);
            //  System.out.println(ISO_FORMAT);
        } catch(DvdStoreException e) {
            logger.error(DVD_NOT_ADDED + e);
            System.out.println(e.getMessage());
        } 

        return "dvd-added";
    }

    @RequestMapping(value="menu-search-dvd", method=RequestMethod.GET)
    public String displaySearchDvdForm() {  
        return "search-dvd";  
    }

    @RequestMapping(value="search-dvd-submit", method=RequestMethod.GET)
    public ModelAndView searchDvd(HttpServletRequest request) {  
  
        Integer dvdId = null;       
        Dvd dvd = null;
        List<Genre> dvdGenres = new ArrayList<Genre>();

        try {
            DvdCollectionService dvdCollectionService = 
                new DvdCollectionServiceImpl();
            dvdId = Integer.parseInt(request.getParameter("dvd-id"));
            dvd = dvdCollectionService.getDvdById(dvdId); 
            dvdGenres = dvd.getGenres();
            // out.println(DVD_SEARCHED);
        } catch (NumberFormatException e) {
            logger.error(DVD_NOT_SEARCHED + e);
            //out.println(DVD_NOT_SEARCHED + dvdId);
        } catch (DvdStoreException e) {
            logger.error(DVD_NOT_SEARCHED + e);
            //out.println(DVD_NOT_SEARCHED + dvdId);            
        }

        return new ModelAndView("search-dvd", "dvd", dvd);  
    }

    @RequestMapping(value="menu-modify-dvd", method=RequestMethod.GET)
    public ModelAndView displayModifyDvdForm(Model model) {  
  
        List<Genre> genreCatalogue = new ArrayList<Genre>();

        try {
            DvdCollectionService dvdCollectionService = 
                new DvdCollectionServiceImpl();
            // Fetch the catalogue        
            genreCatalogue = dvdCollectionService.retrieveGenreCatalogue();
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage());
        } 

        model.addAttribute("dvd", new Dvd());   
        return new ModelAndView("modify-dvd", "genreCatalogue", genreCatalogue);  
    }  

    @RequestMapping(value="modify-dvd-submit", method=RequestMethod.POST)
    public String modifyDvd(@ModelAttribute("dvd")Dvd dvd, BindingResult result, HttpServletRequest request) {  
  
        Integer id = 0;
        boolean isModified = false;
        List<Genre> dvdGenres = new ArrayList();

        try {
            DvdCollectionService dvdCollectionService = 
                new DvdCollectionServiceImpl();

            id = Integer.parseInt(request.getParameter("id"));

            // Gets all the genre IDs as an array of Strings
            String selectedGenres[] = request.getParameterValues("genres");
            for (String genreIdString: selectedGenres) {
                Integer genreId = Integer.parseInt(genreIdString);
                Genre genre = dvdCollectionService.getGenreById(genreId);  
                dvdGenres.add(genre);
            }        
            dvd.setGenres(dvdGenres);
            dvd = dvdCollectionService.modifyDvdDetails(id, dvd);
            // out.println(DVD_UPDATED + id);
        } catch (NumberFormatException e) {
            logger.error(DVD_NOT_UPDATED + e);
            // out.println(DVD_NOT_UPDATED + id);
        } catch (DvdStoreException e) {
            logger.error(DVD_NOT_UPDATED + e);
            // out.println(DVD_NOT_UPDATED + id);            
        }
        return "dvd-added";  
    }


    @RequestMapping(value="menu-remove-dvd", method=RequestMethod.GET)
    public String displayRemoveDvdForm() {  
        return "remove-dvd";  
    }

    @RequestMapping(value="remove-dvd-submit", method=RequestMethod.POST)
    public String removeDvd(HttpServletRequest request) {
        Integer dvdId = null;
        boolean isRemoved = false;

        try {
            DvdCollectionService dvdCollectionService = 
                new DvdCollectionServiceImpl();
            dvdId = Integer.parseInt(request.getParameter("dvd-id"));
            isRemoved = dvdCollectionService.removeDvdById(dvdId); 
            //out.println(DVD_REMOVED + dvdId);
        } catch (NumberFormatException e) {
            logger.error(DVD_NOT_REMOVED + e);
            //out.println(DVD_NOT_REMOVED + dvdId);
        } catch (DvdStoreException e) {
            logger.error(DVD_NOT_REMOVED + e);
            //out.println(DVD_NOT_REMOVED + dvdId);            
        }  
        return "dvd-added";  
    }  

    @RequestMapping(value="menu-recover-dvd", method=RequestMethod.GET)
    public String displayRecoverDvdForm() {  
        return "recover-dvd";  
    }

    @RequestMapping(value="recover-dvd-submit", method=RequestMethod.POST)
    public String recoverDvd(HttpServletRequest request) {
        Integer id = 0;
        boolean isRecovered = false;

        try {
            DvdCollectionService dvdCollectionService = 
                new DvdCollectionServiceImpl();
            // display the archived dvds - To do - might require another native query
            id = Integer.parseInt(request.getParameter("dvd-id"));
            isRecovered = dvdCollectionService.recoverDvd(id); 
            //out.println(DVD_RECOVERED + id);
        } catch (NumberFormatException e) {
            logger.error(DVD_NOT_RECOVERED + e);
            //out.println(DVD_NOT_RECOVERED + id);
        } catch (DvdStoreException e) {
            logger.error(DVD_NOT_RECOVERED + e);
            //out.println(DVD_NOT_RECOVERED + id);            
        }
        return "dvd-added";  
    } 

    @RequestMapping(value="menu-display-dvds", method=RequestMethod.GET)
    public ModelAndView displayAllDvds() {  
        Dvd dvd;
        List<Dvd> dvdCollection = new ArrayList<Dvd>();
        DvdCollectionService dvdCollectionService = 
            new DvdCollectionServiceImpl();

        try {
            dvdCollection = dvdCollectionService.retrieveDvdCollection();
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage());
        } 
        return new ModelAndView("display-all-dvds", "dvdCollection", dvdCollection);  
    }  
}




















