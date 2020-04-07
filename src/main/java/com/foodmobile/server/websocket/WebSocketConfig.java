package com.foodmobile.server.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@ComponentScan("com.foodmobile.server.websocket")
public class WebSocketConfig implements WebSocketConfigurer {
    private SocketInterceptor interceptor = new SocketInterceptor();
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(TextMessageHandler.shared,"/name").addInterceptors(interceptor).setAllowedOrigins("*");
    }
}
