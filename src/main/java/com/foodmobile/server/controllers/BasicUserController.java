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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
    public SimpleStatusResponse updateLocation(HttpServletResponse response, LocationUpdate update){

       if(update.token != null){
           try {
              var token = JsonWebToken.verify(update.token);
               var username = token.get("username");
               //var username = update.username;
               update.username = username;
               usConnections.insert(new Node(username,update.lat,update.lon));
               return SimpleStatusResponse.success();
           }catch(Exception ex){
               if(response != null){
               response.setStatus(HttpStatus.FORBIDDEN.value());
               }
               return SimpleStatusResponse.failure("The specified token is invalid!");
           }
       }else{
           if(response != null) {
               response.setStatus(HttpStatus.UNAUTHORIZED.value());
           }
           return SimpleStatusResponse.failure("No authorization token provided!");
       }

    }

    @PostMapping(path="nearbytrucks",produces = "application/json")
    public List<Node> getLocations(@RequestParam("lat")double lat, @RequestParam("lon")double lon){
        List<Node> nodes = new LinkedList<>();
        var p = new PointLike() {
            @Override
            public double getX() {
                return lon;
            }

            @Override
            public double getY() {
                return lat;
            }
        };
        usConnections.search(p,nodes);
        return nodes;
    }

    @PostMapping(path="offline",produces = "application/json")
    private SimpleStatusResponse goOffline(HttpServletResponse response, @RequestParam("token")String _token){
        if(_token != null){
            try{
            var token = JsonWebToken.verify(_token);
            var username = token.get("username");
            usConnections.remove(username);
            return SimpleStatusResponse.success();
            }catch (Exception ex){
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return SimpleStatusResponse.failure("Token is invalid!");
            }

        }else{
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return SimpleStatusResponse.failure("No authorization token provided!");
        }
    }
}
