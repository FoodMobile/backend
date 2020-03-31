package com.foodmobile.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessageHandlingController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/locations")
    private void receivedLocationUpdate(Principal principal, Object message){
        System.out.print(message);
    }

}
