package com.foodmobile.server.websocket;

import com.foodmobile.server.util.PointLike;
import com.foodmobile.server.util.Quad;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class Node implements PointLike {
    public double lat;
    public double lon;
    private WebSocketSession session = null;
    private Quad parent;
    public Node(WebSocketSession session, double lat, double lon){
        this.lat = lat;
        this.lon = lon;
        this.session = session;
    }

    public void setParent(Quad parent) {
        this.parent = parent;
    }

    public void sendMessage(TextMessageConvertible message){
        try {
            this.session.sendMessage(message.toTextMessage());
        } catch (Exception e) {
            this.removeFromParent();
            e.printStackTrace();
        }
    }

    private void removeFromParent(){
        if(parent == null)
            return;
        this.parent.removeNode(this);
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
