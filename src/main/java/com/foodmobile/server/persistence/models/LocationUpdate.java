package com.foodmobile.server.persistence.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodmobile.server.util.PointLike;
import com.foodmobile.server.websocket.TextMessageConvertible;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;

public class LocationUpdate implements PointLike, TextMessageConvertible {
    public double lat;
    public double lon;
    public String id;

    @Override
    public double getX() {
        return lon;
    }

    @Override
    public double getY() {
        return lat;
    }

    @Override
    public TextMessage toTextMessage() {
        var payloadObj = new HashMap<String,Object>(){{put("lat",lat);put("lon",lon);put("id",id);}};
        ObjectMapper mapper = new ObjectMapper();
        try {
            var payloadString = mapper.writeValueAsString(payloadObj);
            return new TextMessage(payloadString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new TextMessage("");
    }

    @Override
    public void fromTextMessage(TextMessage message) {

    }
}
