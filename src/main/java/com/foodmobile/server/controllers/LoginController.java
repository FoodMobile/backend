package com.foodmobile.server.controllers;

import com.foodmobile.server.auth.loginmodels.AuthTypes;
import com.foodmobile.server.auth.loginmodels.FacebookPayload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public void login(@RequestBody com.foodmobile.server.Auth.loginmodels.LoginRequest request){
        if(request.type.equals(AuthTypes.FACEBOOK.rawValue)){
            try {
                FacebookPayload fb = new FacebookPayload(request.payload);
                System.out.println(fb.oauth);
            }catch (Exception e){}
        }else if(request.type.equals(AuthTypes.BASIC.rawValue)){

        }else if(request.type.equals(AuthTypes.GOOGLE.rawValue)){

        }
    }
}
