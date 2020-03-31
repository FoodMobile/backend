package com.foodmobile.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessageHandlingController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/news")
    @SendTo("/topic/news")
    private String receivedLocationUpdate(@Payload String message){
        System.out.print(message);
        return message;
    }

}
