package com.foodmobile.server.websocket;


import com.foodmobile.databaselib.DatabaseAdapter;
import com.foodmobile.server.util.Quad;
import com.foodmobile.server.util.Rect;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class TextMessageHandler extends TextWebSocketHandler {

    Quad usConnections = new Quad(new Rect(-128,49,61,24));

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

        String clientMessage = message.getPayload();
        // get lat and lon from message
        var lat = -128;
        var lon = 49;
        // Send to all people in the surrounding area. We will change this to be able to be used in other parts of the server (rest requests etc.)
        // but for now this is an example of functionality.
        this.usConnections.broadCast(lat,lon,message);

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        //get user and location from JWT
        var lat = -128 ;// dummy value
        var lon = 49;
        var node = new Node(session,lat,lon);
        this.usConnections.insert(node);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}








