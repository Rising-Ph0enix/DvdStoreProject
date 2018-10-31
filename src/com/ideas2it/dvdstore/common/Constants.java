package com.ideas2it.dvdstore.common;

/** 
 * Contains constants that are reused across classes, 
 * common to the dvdstore package
 * 
 */
public final class Constants {
    private Constants(){}

    /**
     * Dvd Table column constants
     */
    public static final String DB_ID = "id";
    public static final String DB_MOVIE_NAME = "movie_name";
    public static final String DB_RELEASE_DATE = "release_date";
    public static final String DB_LANGUAGE = "language";
    public static final String DB_GENRE = "genre";
    public static final String DB_PRICE = "price";
    public static final String DB_QUANTITY = "quantity";

    /**
     * Genre Table column constants
     */
    public static final String DB_GENRE_ID = "id";
    public static final String DB_GENRE_NAME = "name";

    /** 
     * Dvd object attribute constants
     */
    public static final String DVD_ID = "\n ID: ";
    public static final String DVD_MOVIE_NAME = "Movie Name: ";
    public static final String DVD_RELEASE_DATE = "Release Date: ";
    public static final String DVD_LANGUAGE = "Language: ";
    public static final String DVD_GENRE = "Genre: ";
    public static final String DVD_PRICE = "Price: ";
    public static final String DVD_QUANTITY = "Quantity: ";

    /** 
     * Genre object attribute constants
     */
    public static final String GENRE_ID = "\n ID: ";
    public static final String GENRE_NAME = "Genre Name: ";
    
    /** 
     * Dvd DAO CustomException constants
     */
    public static final String DVD_NOT_ADDED = "The DVD could not be added";
    public static final String DVD_NOT_SEARCHED = "The DVD could not be searched";
    public static final String DVD_NOT_UPDATED = "The DVD could not be updated";
    public static final String DVD_NOT_REMOVED = "The DVD could not be removed";
    public static final String DVD_NOT_RECOVERED = "The DVD could not be recovered";
    public static final String DVD_NOT_DISPLAYED = "The DVD could not be displayed";

    /** 
     * Genre DAO CustomException constants
     */
    public static final String GENRE_ALREADY_EXISTS = "That genre already exists";
    public static final String GENRE_NOT_ADDED = "The Genre could not be added";
    public static final String GENRE_NOT_SEARCHED = "The Genre could not be searched";
    public static final String GENRE_NOT_UPDATED = "The Genre could not be updated";
    public static final String GENRE_NOT_REMOVED = "The Genre could not be removed";
    public static final String GENRE_NOT_RECOVERED = "The Genre could not be recovered";
    public static final String GENRE_NOT_DISPLAYED = "The Genre could not be displayed";

    /** 
     * Customer DAO CustomException constants
     */
    public static final String CUSTOMER_ALREADY_EXISTS = "That customer already exists";
    public static final String CUSTOMER_NOT_ADDED = "The customer could not be added";
    public static final String CUSTOMER_NOT_SEARCHED = "The customer could not be searched";
    public static final String CUSTOMER_NOT_UPDATED = "The customer could not be updated";
    public static final String CUSTOMER_NOT_REMOVED = "The customer could not be removed";
    public static final String CUSTOMER_NOT_RECOVERED = "The customer could not be recovered";
    public static final String CUSTOMER_NOT_DISPLAYED = "The customer could not be displayed";

    /** 
     * Order DAO CustomException constants
     */
    public static final String ORDER_ALREADY_EXISTS = "That order already exists";
    public static final String ORDER_NOT_ADDED = "The order could not be added";
    public static final String ORDER_NOT_SEARCHED = "The order could not be searched";
    public static final String ORDER_NOT_UPDATED = "The order could not be updated";
    public static final String ORDER_NOT_REMOVED = "The order could not be removed";
    public static final String ORDER_NOT_RECOVERED = "The order could not be recovered";
    public static final String ORDER_NOT_DISPLAYED = "The order could not be displayed";

    /**
     * Dvd Controller: Prompt input and display result message constants 
     */
    public static final String PROMPT_MOVIE_NAME = "Enter the movie name: ";
    public static final String PROMPT_RELEASE_DATE = "Enter the release date: ";
    public static final String PROMPT_LANGUAGE = "Enter the language: ";
    public static final String PROMPT_PRICE = "Enter the price: ";
    public static final String PROMPT_QUANTITY = "Enter the quantity: ";
    public static final String PROMPT_GENRE_ID = "Choose an option from the catalogue: ";
    public static final String PROMPT_VALID_RDATE = "Enter a valid release date: ";
    public static final String PROMPT_ADD_GENRE = "Do you want to add another genre? Y/N";
    public static final String PROMPT_DVD_SEARCH = "Enter the movie name of the dvd that you want to search for: ";
    public static final String PROMPT_DVD_MODIFY = "Enter the id of the dvd that you want to modify: ";
    public static final String PROMPT_DVD_REMOVE = "Enter the id of the dvd that you want to remove: ";

    public static final String DVD_ADDED = "The DVD has been added";
    public static final String DVD_UPDATED = "The DVD has been updated";
    public static final String DVD_REMOVED = "The DVD has been removed";
    public static final String DVD_RECOVERED = "The DVD has been recovered";

    public static final String MOVIE_RELEASED = "\n Movie released ";
    public static final String YEARS = " years, ";
    public static final String MONTHS = " months, ";
    public static final String DAYS = " days ago";

    public static final String NO_SUCH_DVD = "There is no DVD with the given id";
    public static final String ISO_FORMAT = "Release date must be in dd/mm/yyyy format";
    public static final String N = "N";
    public static final String CHOOSE_ID = " (Choose an ID from the menu)";
    public static final String ALPHA_PATTERN = "[a-zA-Z]+";
    // HTML 5 date - not working with uuuu/MM/dd
    public static final String DATE_FORMAT = "uuuu-MM-dd";
    public static final String DVDS = "Dvds: ";
    public static final String GENRES = "Genres: ";

    /**
     * GenreCatalogueController: Prompt input and display result message constants 
     */
    public static final String PROMPT_GENRE_NAME = "Enter the genre name: ";
    public static final String PROMPT_GENRE_SEARCH = "Enter the name of the genre that you want to search for: ";
    public static final String PROMPT_GENRE_MODIFY = "Enter the id of the genre that you want to modify: ";
    public static final String PROMPT_GENRE_REMOVE = "Enter the id of the genre that you want to remove: ";

    public static final String GENRE_ADDED = "The Genre name has been added";
    public static final String GENRE_UPDATED = "The Genre name has been updated";
    public static final String GENRE_REMOVED = "The Genre name has been removed";
    public static final String GENRE_RECOVERED = "The Genre has been recovered";


    /**
     * CustomerController: Prompt input and display result message constants 
     */
    public static final String CUSTOMER_ADDED = "The Customer name has been added";
    public static final String CUSTOMER_UPDATED = "The Customer name has been updated";
    public static final String CUSTOMER_REMOVED = "The Customer name has been removed";
    public static final String CUSTOMER_RECOVERED = "The Customer has been recovered";

    /**
     * Dvd Service: Custom Exception Message constants
     */
    public static final String DATE_EXCEPTION = 
        "Couldn't calculate duration between dates";

    /**
     * DvdStore Application Menu constants
     */
    public static final String DVD_STORE_CONSOLE = 
        "\n\t\t\tDVD Store Management Console\n";    
    public static final String DVD_ADD = 
        "1. Add a DVD to the DVD collection\n";
    public static final String DVD_SEARCH = 
        "2. Search for a DVD\n";
    public static final String DVD_MODIFY = 
        "3. Modify details of a DVD in the collection\n";
    public static final String DVD_REMOVE = 
        "4. Remove a DVD from the collection\n";
    public static final String DVD_RECOVER = 
        "5. Recover a DVD from the collection\n";
    public static final String DVD_DISPLAY = "6. Display the collection of DVDs\n";

    public static final String GENRE_ADD = "1. Add a genre to the catalogue \n";
    public static final String GENRE_SEARCH = "2. Search for a Genre\n";
    public static final String GENRE_MODIFY = "3. Modify a Genre\n";
    public static final String GENRE_REMOVE = "4. Remove a Genre\n";
    public static final String GENRE_RECOVER = "5. Recover a Genre\n";
    public static final String GENRE_DISPLAY = "6. Display the Genre catalogue\n";

    public static final String MENU_EXIT = "5. Exit\n";

    public static final String MENU_VALID_CHOICE = "Enter a valid choice";

    /**
     * General validation message constants
     */
    public static final String CANT_BE_BLANK = "This field can't be blank";
    public static final String ONLY_LETTERS = "This field can contain only letters";
    public static final String ONLY_NUMBERS = "This field can contain only decimal numbers";
    public static final String NON_NEGATIVE = "This field can't be negative";

    /**
     * Comma constant
     */
    public static final String COMMA = ", ";
}
