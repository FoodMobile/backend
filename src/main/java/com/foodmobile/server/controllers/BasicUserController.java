package com.foodmobile.server.controllers;

import com.foodmobile.server.datamodels.LocationUpdate;
import com.foodmobile.server.datamodels.SimpleStatusResponse;

import com.foodmobile.server.util.jwt.JsonWebToken;
import com.foodmobile.server.websocket.TextMessageHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class BasicUserController {
    @GetMapping(path="/profile/update", produces="application/json")
    public SimpleStatusResponse profileUpdate(@RequestParam String token) {
        // TODO: Decide what content needs to be updated in the profile
        return SimpleStatusResponse.success();
    }

    @PostMapping
    public SimpleStatusResponse updateLocation(ServerHttpRequest  serverHttpRequest, ServerHttpResponse response, LocationUpdate update){
       var tokens = serverHttpRequest.getHeaders().getOrDefault("token",new LinkedList<>());
       if( tokens.size() > 0){
           try {
               var token = JsonWebToken.verify(tokens.get(0));
               var username = token.get("username");
               update.username = username;
               TextMessageHandler.shared.queueUpdate(update);
               return SimpleStatusResponse.success();
           }catch(Exception ex){
               response.setStatusCode(HttpStatus.FORBIDDEN);
               return SimpleStatusResponse.failure("The specified token is invalid!");
           }
       }else{
           response.setStatusCode(HttpStatus.UNAUTHORIZED);
           return SimpleStatusResponse.failure("No authorization token provided!");
       }

    }
}
