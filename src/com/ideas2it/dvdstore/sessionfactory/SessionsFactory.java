package com.ideas2it.dvdstore.sessionfactory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton class to manage the creation of database connection
 *
 */ 
public class SessionsFactory {

    /* Sole instance variable of the class */
    private static SessionsFactory sessionsFactory = null;
    
    private static SessionFactory factory;
    
    /* For the sole purpose of preventing external object creation */ 
    private SessionsFactory() {
            
    } 

    /* Creates one instance of the class and returns it */
    public static SessionsFactory getInstance() {
        if (null == sessionsFactory) {
            sessionsFactory = new SessionsFactory();
        }
        return sessionsFactory;
    }

    /* 
     * Synchronized to make it thread safe
     * Creates one instance of the SessionFactory and returns it  
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (null == factory) {
            factory = new Configuration().configure().buildSessionFactory();
        }
        return factory;
    }  
}
