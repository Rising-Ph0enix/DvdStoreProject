package com.ideas2it.dvdstore.controller;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
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
import com.ideas2it.dvdstore.service.GenreCatalogueService;
import com.ideas2it.dvdstore.service.impl.GenreCatalogueServiceImpl;

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
@RequestMapping("genrecontroller") 
public class GenreCatalogueController {

    DvdStoreLogger logger = new DvdStoreLogger();

    @RequestMapping(value="menu-add-genre", method=RequestMethod.GET)
    public String displayAddGenreForm(Model model) {  
        model.addAttribute("genre", new Genre());     
        return "add-genre";  
    }

    @RequestMapping(value="add-genre-submit", method=RequestMethod.POST)
    public String addGenre(@ModelAttribute("genre")Genre genre, BindingResult result) {  
  
        GenreCatalogueServiceImpl genreCatalogueService = 
            new GenreCatalogueServiceImpl();

        try {
            genre = genreCatalogueService.addGenre(genre); 
            // out.println(GENRE_ADDED + genre.getName());
        } catch(DvdStoreException e) {
            logger.error(GENRE_NOT_ADDED + e);
            System.out.println(e.getMessage());
        } 

        return "dvd-added";
    }

    @RequestMapping(value="menu-search-genre", method=RequestMethod.GET)
    public String displaySearchGenreForm() {  
        return "search-genre";  
    }

    @RequestMapping(value="search-genre-submit", method=RequestMethod.GET)
    public ModelAndView searchGenre(ModelMap map, HttpServletRequest request) {  
  
        Integer genreId = null;       
        Genre genre = null;
        List<Dvd> genreDvds = new ArrayList<Dvd>();

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();
            genreId = Integer.parseInt(request.getParameter("genre-id"));
            genre = genreCatalogueService.getGenreById(genreId);
            genreDvds = genre.getDvds();
        } catch (NumberFormatException e) {
            logger.error(GENRE_NOT_SEARCHED + e);
            // out.println(GENRE_NOT_SEARCHED + genreId);
        } catch (DvdStoreException e) {
            logger.error(GENRE_NOT_SEARCHED + e);
            // out.println(GENRE_NOT_SEARCHED + genreId);            
        }

        return new ModelAndView("search-genre", "genre", genre);  
    }

    @RequestMapping(value="menu-modify-genre", method=RequestMethod.GET)
    public String displayModifyGenreForm(Model model) {  
        model.addAttribute("genre", new Genre());   
        return "modify-genre";  
    }

    @RequestMapping(value="modify-genre-submit", method=RequestMethod.POST)
    public String modifyGenre(@ModelAttribute("genre")Genre genre, BindingResult result, HttpServletRequest request) {  
  
        Integer id = 0;
        boolean isModified = false;

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();

            // display the archived genres - To do - might require another native query
            id = Integer.parseInt(request.getParameter("id"));
        
            genre = genreCatalogueService.modifyGenreDetails(id, genre);
            // out.println(GENRE_UPDATED + id);
        } catch (NumberFormatException e) {
            logger.error(GENRE_NOT_UPDATED + e);
            // out.println(GENRE_NOT_UPDATED + id);
        } catch (DvdStoreException e) {
            logger.error(GENRE_NOT_UPDATED + e);
            // out.println(GENRE_NOT_UPDATED + id);            
        }
        return "dvd-added";
    }

    @RequestMapping(value="menu-remove-genre", method=RequestMethod.GET)
    public String displayRemoveGenreForm() {  
        return "remove-genre";  
    }

    @RequestMapping(value="remove-genre-submit", method=RequestMethod.POST)
    public String removeGenre(HttpServletRequest request) {
        Integer id = null;
        boolean isDeleted = false;

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();
            id = Integer.parseInt(request.getParameter("genre-id"));
            isDeleted = genreCatalogueService.removeGenreById(id); 
            // out.println(GENRE_REMOVED + id);
        } catch (NumberFormatException e) {
            logger.error(GENRE_NOT_REMOVED + e);
            // out.println(GENRE_NOT_REMOVED + id);
        } catch (DvdStoreException e) {
            logger.error(GENRE_NOT_REMOVED + e);
            // out.println(GENRE_NOT_REMOVED + id);            
        }
        return "dvd-added";  
    }  

    @RequestMapping(value="menu-recover-genre", method=RequestMethod.GET)
    public String displayRecoverGenreForm() {  
        return "recover-genre";  
    }

    @RequestMapping(value="recover-genre-submit", method=RequestMethod.POST)
    public String recoverGenre(HttpServletRequest request) {
        Integer id = 0;
        boolean isRecovered = false;

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();
            // display the archived dvds - To do - might require another native query
            id = Integer.parseInt(request.getParameter("genre-id"));
            isRecovered = genreCatalogueService.recoverGenre(id); 
            // out.println(GENRE_RECOVERED + id);
        } catch (NumberFormatException e) {
            logger.error(GENRE_NOT_RECOVERED + e);
            // out.println(GENRE_NOT_RECOVERED + id);
        } catch (DvdStoreException e) {
            logger.error(GENRE_NOT_RECOVERED + e);
            // out.println(GENRE_NOT_RECOVERED + id);            
        } 
        return "dvd-added";  
    } 

    @RequestMapping(value="menu-display-genres", method=RequestMethod.GET)
    public ModelAndView displayAllGenres() {  
        List<Genre> genreCatalogue = new ArrayList<Genre>();

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();
            genreCatalogue = genreCatalogueService.retrieveGenreCatalogue();
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage());
        } 
        return new ModelAndView("display-all-genres", "genreCatalogue", genreCatalogue);  
    }  

  
}

/* 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
 
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();

        String userChoice = request.getParameter("genremenu-button");
       
        switch(userChoice) {
            case "menu-add-genre":
                displayAddGenreForm(request, response);
                break;
            case "add-genre-submit":
                addGenre(request, response, out);
                break;
            case "menu-search-genre":
                displaySearchGenreForm(request, response);
                break;
            case "search-genre-submit":
                searchGenre(request, response, out);
                break;
            case "menu-remove-genre":
                displayRemoveGenreForm(request, response);
                break;
            case "remove-genre-submit":
                removeGenre(request, response, out);
                break;
            case "menu-recover-genre":
                displayRecoverGenreForm(request, response);
                break;
            case "recover-genre-submit":
                recoverGenre(request, response, out);
                break;
            case "menu-display-genres":
                displayAllGenres(request, response);
                break;
            case "menu-modify-genre":
                displayModifyGenreForm(request, response);
                break;
            case "modify-genre-submit":
                modifyGenre(request, response, out);
                break;
        }
    } 

    public void displayAddGenreForm(HttpServletRequest request, HttpServletResponse response) {
        dispatchRequest("add-genre.jsp", request, response);
    }

    public void addGenre(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        Genre genre = new Genre();
        GenreCatalogueServiceImpl genreCatalogueService = 
            new GenreCatalogueServiceImpl();
        // List<Dvd> genreDvds = new ArrayList();

        try {
            genre.setName(request.getParameter("genre-name"));
            // expired date is nullable by default
            genre = genreCatalogueService.addGenre(genre); 
            out.println(GENRE_ADDED + genre.getName());
        } catch(DvdStoreException e) {
            logger.error(GENRE_NOT_ADDED + e);
            System.out.println(e.getMessage());
        } 
    }

    public void displaySearchGenreForm(HttpServletRequest request, HttpServletResponse response) {
        dispatchRequest("search-genre.jsp", request, response);
    }

    public void searchGenre(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        Integer genreId = null;       
        Genre genre = null;
        List<Dvd> genreDvds = new ArrayList<Dvd>();

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();
            genreId = Integer.parseInt(request.getParameter("genre-id"));
            genre = genreCatalogueService.getGenreById(genreId);
            genreDvds = genre.getDvds();
        } catch (NumberFormatException e) {
            logger.error(GENRE_NOT_SEARCHED + e);
            out.println(GENRE_NOT_SEARCHED + genreId);
        } catch (DvdStoreException e) {
            logger.error(GENRE_NOT_SEARCHED + e);
            out.println(GENRE_NOT_SEARCHED + genreId);            
        }

        request.setAttribute("genre", genre);
        request.setAttribute("genreDvds", genreDvds);
        dispatchRequest("search-genre.jsp", request, response);
    }

    public void displayRemoveGenreForm(HttpServletRequest request, HttpServletResponse response) {
        dispatchRequest("remove-genre.jsp", request, response);
    }

    public void removeGenre(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        Integer id = null;
        boolean isDeleted = false;

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();
            id = Integer.parseInt(request.getParameter("genre-id"));
            isDeleted = genreCatalogueService.removeGenreById(id); 
            out.println(GENRE_REMOVED + id);
        } catch (NumberFormatException e) {
            logger.error(GENRE_NOT_REMOVED + e);
            out.println(GENRE_NOT_REMOVED + id);
        } catch (DvdStoreException e) {
            logger.error(GENRE_NOT_REMOVED + e);
            out.println(GENRE_NOT_REMOVED + id);            
        }
    }

    public void displayRecoverGenreForm(HttpServletRequest request, HttpServletResponse response) {
        dispatchRequest("recover-genre.jsp", request, response);
    }

    public void recoverGenre(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        Integer id = 0;
        boolean isRecovered = false;

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();
            // display the archived dvds - To do - might require another native query
            id = Integer.parseInt(request.getParameter("genre-id"));
            isRecovered = genreCatalogueService.recoverGenre(id); 
            out.println(GENRE_RECOVERED + id);
        } catch (NumberFormatException e) {
            logger.error(GENRE_NOT_RECOVERED + e);
            out.println(GENRE_NOT_RECOVERED + id);
        } catch (DvdStoreException e) {
            logger.error(GENRE_NOT_RECOVERED + e);
            out.println(GENRE_NOT_RECOVERED + id);            
        }
    } 

    public void displayModifyGenreForm(HttpServletRequest request, HttpServletResponse response) {
        dispatchRequest("modify-genre.jsp", request, response);
    }

    public void modifyGenre(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        Integer id = 0;
        boolean isModified = false;
        Genre genre = new Genre();

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();

            // display the archived dvds - To do - might require another native query
            id = Integer.parseInt(request.getParameter("genre-id"));
            genre.setName(request.getParameter("genre-name"));
            // expired date is nullable by default
            System.out.println(genre);
        
            genre = genreCatalogueService.modifyGenreDetails(id, genre);
            out.println(GENRE_UPDATED + id);
        } catch (NumberFormatException e) {
            logger.error(GENRE_NOT_UPDATED + e);
            out.println(GENRE_NOT_UPDATED + id);
        } catch (DvdStoreException e) {
            logger.error(GENRE_NOT_UPDATED + e);
            out.println(GENRE_NOT_UPDATED + id);            
        }
    } 

    public void displayAllGenres(HttpServletRequest request, HttpServletResponse response) {
        List<Genre> genreCatalogue = new ArrayList<Genre>();

        try {
            GenreCatalogueServiceImpl genreCatalogueService = 
                new GenreCatalogueServiceImpl();
            genreCatalogue = genreCatalogueService.retrieveGenreCatalogue();
        } catch (DvdStoreException e) {
            System.out.println(e.getMessage());
        } 
        request.setAttribute("genreCatalogue", genreCatalogue);
        dispatchRequest("display-all-genres.jsp", request, response);
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

 


