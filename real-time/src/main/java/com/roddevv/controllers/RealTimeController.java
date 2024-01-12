package com.roddevv.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeController {
    @MessageMapping("/events")
    @SendTo("/events/updates")
    public String sendMessage(String message) {
        return "You sent: " + message;
    }
}
