package com.foodmobile.server.controllers;

import com.foodmobile.server.datamodels.LocationUpdate;
import com.foodmobile.server.datamodels.SimpleStatusResponse;

import com.foodmobile.server.util.PointLike;
import com.foodmobile.server.util.Quad;
import com.foodmobile.server.util.Rect;
import com.foodmobile.server.util.jwt.JsonWebToken;

import com.foodmobile.server.util.Node;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class BasicUserController {
    @PostMapping(path="/profile/update", produces="application/json")
    public SimpleStatusResponse profileUpdate(@RequestParam String token) {
        // TODO: Decide what content needs to be updated in the profile
        return SimpleStatusResponse.success();
    }

    Quad usConnections = new Quad(new Rect(-128,49,61,24));

    @PostMapping(value = "trucklocation",produces = "application/json")
    public SimpleStatusResponse updateLocation(ServerHttpRequest  serverHttpRequest, ServerHttpResponse response, LocationUpdate update){
       var tokens = serverHttpRequest.getHeaders().getOrDefault("token",new LinkedList<>());
       if( tokens.size() > 0){
           try {
               var token = JsonWebToken.verify(tokens.get(0));
               var username = token.get("username");
               update.username = username;
               usConnections.insert(new Node(username,update.lat,update.lon));
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

    @PostMapping(path="nearbytrucks",produces = "application/json")
    public List<Node> getLocations(double lat, double lon){
        List<Node> nodes = new LinkedList<>();
        var p = new PointLike() {
            @Override
            public double getX() {
                return lat;
            }

            @Override
            public double getY() {
                return lon;
            }
        };
        usConnections.search(p,nodes);
        return nodes;
    }

    @PostMapping(path="offline",produces = "application/json")
    private SimpleStatusResponse goOffline(ServerHttpRequest request,ServerHttpResponse response){
        var tokens = request.getHeaders().getOrDefault("token",new LinkedList<>());
        if(tokens.size() > 0){
            try{
            var token = JsonWebToken.verify(tokens.get(0));
            var username = token.get("username");
            usConnections.remove(username);
            return SimpleStatusResponse.success();
            }catch (Exception ex){
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return SimpleStatusResponse.failure("Token is invalid!");
            }

        }else{
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return SimpleStatusResponse.failure("No authorization token provided!");
        }
    }
}
