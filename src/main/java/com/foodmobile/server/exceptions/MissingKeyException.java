package com.foodmobile.server.exceptions;

public class MissingKeyException extends Exception{
    private String key;
    public MissingKeyException(String key){
        super();
        this.key = key;
    }
    @Override
    public String toString() {
        return String.format("Missing key %s",key);
    }
}
