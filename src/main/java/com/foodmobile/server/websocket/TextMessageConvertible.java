package com.foodmobile.server.websocket;

import org.springframework.web.socket.TextMessage;

public interface TextMessageConvertible {
    public TextMessage toTextMessage();
    public void fromTextMessage(TextMessage message);
}
