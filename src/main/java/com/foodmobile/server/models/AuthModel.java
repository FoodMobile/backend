package com.foodmobile.server.models;
import com.foodmobile.server.auth.loginmodels.*;

import javax.servlet.http.HttpServletRequest;

public class AuthModel {
    public static AuthModel shared = new AuthModel();

    public void authWithBasic(BasicAuthPayload payload, HttpServletRequest request){

    }

    public void authWithFacebook(FacebookPayload payload, HttpServletRequest request){

    }

    public void authWithGoogle(GooglePayload payload, HttpServletRequest request){

    }
}
