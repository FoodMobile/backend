package com.foodmobile.server.controllers;

import com.foodmobile.server.Auth.LoginModels.FacebookPayload;
import com.foodmobile.server.Auth.LoginModels.LoginRequest;
import com.foodmobile.server.Utils.LinkedHashMapDecoder;
import com.foodmobile.server.datamodels.ApiTestObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;







@RestController
public class ApiController {
    @GetMapping(path = "/api/test", produces="application/json")
    public ApiTestObject test() {
        return new ApiTestObject();
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request){
        if(request.type.equals("facebook")){
            try {
                FacebookPayload fb = LinkedHashMapDecoder.decode(request.payload, FacebookPayload.class);
                System.out.println(fb.oauth);
            }catch (Exception e){}
        }
    }
}