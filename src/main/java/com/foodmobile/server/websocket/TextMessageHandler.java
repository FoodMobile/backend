package com.foodmobile.server.websocket;


import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TextMessageHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

        String clientMessage = message.getPayload();

        if (clientMessage.startsWith("Hello") || clientMessage.startsWith("Hi")) {
            session.sendMessage(new TextMessage("Hello! What can i do for you?"));
        } else {
            session.sendMessage(
                    new TextMessage("This is a simple hello world example of using Spring WebSocket."));
        }
    }
}
