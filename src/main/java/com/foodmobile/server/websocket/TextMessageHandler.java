package com.foodmobile.server.websocket;


import com.foodmobile.server.persistence.models.LocationUpdate;
import com.foodmobile.server.util.Quad;
import com.foodmobile.server.util.Rect;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentLinkedQueue;


public class TextMessageHandler extends TextWebSocketHandler {
    public static final TextMessageHandler shared = new TextMessageHandler();
    final ConcurrentLinkedQueue<LocationUpdate> locationQueue = new ConcurrentLinkedQueue<>();
    Thread senderThread;
    private TextMessageHandler(){
        this.senderThread = new Thread(this::sendUpdates);
        this.senderThread.setDaemon(true);
        this.senderThread.start();
    }
    public final Quad usConnections = new Quad(new Rect(-128,49,61,24));


    public void queueUpdate(LocationUpdate update){
        synchronized (this.locationQueue){
            locationQueue.add(update);
        }
    }

    private void sendUpdates(){
        while(true){
            synchronized (this.locationQueue){
                var update = this.locationQueue.poll();
                this.usConnections.broadCast(update);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        // TODO: change this to use the location of the user equated with this session. See com.foodmobile.server.websocket.SocketInterceptor to intercept the websocket
        // Example:
        var lat = -128 ;// dummy value
        var lon = 49;// dummy value
        var node = new Node(session,lat,lon);
        this.usConnections.insert(node);

    }

}








