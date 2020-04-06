package com.foodmobile.server.websocket;

import com.foodmobile.server.util.PointLike;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class Node implements PointLike {
    public double lat;
    public double lon;
    private WebSocketSession session = null;
    public Node(WebSocketSession session,double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.session = session;
    }
    public void sendMessage(TextMessage message){
        try {
            this.session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getX() {
        return lat;
    }

    @Override
    public double getY() {
        return lon;
    }


}
