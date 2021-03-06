package com.foodmobile.server.datamodels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodmobile.server.util.JsonSerializer;
import com.foodmobile.server.util.PointLike;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;
import java.util.Map;

public class LocationUpdate implements PointLike {
    public double lat;
    public double lon;
    public String token;
    public String username;

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public double getX() {
        return lon;
    }

    @Override
    public double getY() {
        return lat;
    }

//    @Override
//    public TextMessage toTextMessage() {
//        var payloadObj = new HashMap<String,Object>(){{put("lat",lat);put("lon",lon);put("id", username);}};
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            var payloadString = mapper.writeValueAsString(payloadObj);
//            return new TextMessage(payloadString);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return new TextMessage("");
//    }
//
//    @Override
//    public void fromTextMessage(TextMessage message) {
//        var payloadStr = message.getPayload();
//        JsonSerializer.deserialize(payloadStr,Map.class).ifPresent(map ->{
//            try {
//                this.username = (String) map.getOrDefault("id", null);
//                this.lat = (double)map.getOrDefault("lat",0);
//                this.lon = (double)map.getOrDefault("lon",0);
//            }catch (Exception ex){}
//        });
//    }
}
