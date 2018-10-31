package com.ideas2it.dvdstore.exception;

public class DvdStoreException extends Exception {
    public DvdStoreException(String customMessage) {
        // Call the super class constructor
        super(customMessage);
    }

    public DvdStoreException(String customMessage, Throwable cause) {
        // Call the super class constructor
        super(customMessage, cause);
    }
}
