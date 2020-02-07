package com.foodmobile.server.auth.loginmodels;

public enum AuthTypes {
    BASIC("basic"),
    FACEBOOK("facebook"),
    GOOGLE("google");
    public String rawValue;
    AuthTypes(String rawValue){
        this.rawValue = rawValue;
    }
}
