package com.foodmobile.server.controllers;

import com.foodmobile.server.auth.loginmodels.*;
import com.foodmobile.server.exceptions.MissingKeyException;
import com.foodmobile.server.models.AuthModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    @PostMapping("/login")
    public void login(HttpServletRequest httpRequest, @RequestBody LoginRequest payload) throws MissingKeyException {
        if(payload.type.equals(AuthTypes.FACEBOOK.rawValue)){
            FacebookPayload fb = new FacebookPayload(payload.payload);
            AuthModel.shared.authWithFacebook(fb,httpRequest);
        }else if(payload.type.equals(AuthTypes.BASIC.rawValue)){
            BasicAuthPayload basic = new BasicAuthPayload(payload.payload);
            AuthModel.shared.authWithBasic(basic,httpRequest);
        }else if(payload.type.equals(AuthTypes.GOOGLE.rawValue)){
            GooglePayload googlePayload = new GooglePayload(payload.payload);
            AuthModel.shared.authWithGoogle(googlePayload,httpRequest);
        }
    }
}
