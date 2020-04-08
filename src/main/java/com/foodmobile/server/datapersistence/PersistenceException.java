package com.foodmobile.server.datapersistence;

public class PersistenceException extends Exception {
    public PersistenceException() {
        super();
    }

    public PersistenceException(Exception ex) {
        super(ex);
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Exception ex) {
        super(message, ex);
    }
}
