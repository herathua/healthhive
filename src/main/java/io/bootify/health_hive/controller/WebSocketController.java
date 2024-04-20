package io.bootify.health_hive.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/lab/{id}")
    @SendTo("/topic/lab/{id}")
    public String handleLabUpdate(String message) {
        // Handle lab update request here
        return "Lab data updated"; // You can send any response back to clients
    }
}
