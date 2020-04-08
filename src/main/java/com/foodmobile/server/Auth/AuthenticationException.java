package com.foodmobile.server.auth;

public class AuthenticationException extends Exception{
    public static AuthenticationException invalidCredentials() {
        return new AuthenticationException("Invalid username or password");
    }

    public AuthenticationException(String message, Exception parent) {
        super(message, parent);
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Exception parent) {
        super(parent);
    }

    public AuthenticationException() {
        super();
    }
}