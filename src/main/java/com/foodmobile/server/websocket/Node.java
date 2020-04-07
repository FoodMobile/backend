package com.foodmobile.server.websocket;

import com.foodmobile.server.events.Event;
import com.foodmobile.server.events.EventHandler;
import com.foodmobile.server.persistence.models.LocationUpdate;
import com.foodmobile.server.util.PointLike;
import com.foodmobile.server.util.Quad;
import org.springframework.web.socket.WebSocketSession;

public class Node implements PointLike {
    public double lat;
    public double lon;
    private WebSocketSession session = null;
    private Quad parent;

    private String user_id = null;

    private String sessionId = null;

    public Node(WebSocketSession session, double lat, double lon) throws Exception {
        this.lat = lat;
        this.lon = lon;
        this.session = session;
        if(session == null)
            throw new Exception("Connection cannot be null!");
        try {
            user_id = (String) session.getAttributes().getOrDefault("USER_ID", null);
            if (user_id != null) {
                EventHandler.shared.addListener(user_id, this, this::onUserEvent);
            }
            sessionId = session.getId();
            if(sessionId != null)
                EventHandler.shared.addListener(session.getId(),this,this::onConnectionStateChanged);

        }catch(Exception e){
            throw new Exception("Error adding user event handler to node!");
        }

    }

    public void setParent(Quad parent) {
        this.parent = parent;
    }

    public void sendMessage(TextMessageConvertible message){
        try {
            this.session.sendMessage(message.toTextMessage());
        } catch (Exception e) {
            this.destroyNode();
            e.printStackTrace();
        }
    }

    private void onConnectionStateChanged(Event e){
        this.destroyNode();
    }

    private void destroyNode(){
        EventHandler.shared.removeListener(sessionId,this);
        EventHandler.shared.removeListener(user_id,this);
        this.removeFromParent();
    }

    private void onUserEvent(Event e){
        if(e.payload instanceof LocationUpdate){
            var update = (LocationUpdate)e.payload;
            this.lat = update.lat;
            this.lon = update.lon;
            this.parent.restructureNode(this);
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
