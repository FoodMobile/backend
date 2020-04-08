package com.foodmobile.server.websocket;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.foodmobile.server.util.jwt.JsonWebToken;

public class SocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        try {
            var tokenArr = serverHttpRequest.getHeaders().get("token");
            if (tokenArr.size() > 0) {
                var token = tokenArr.get(0);
                var reader = JsonWebToken.verify(token);
                var userId = reader.getInt("id");
                attributes.put("id", userId);
                return true;
            } else {
                return false;
            }
        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
