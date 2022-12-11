package com.revature.controllers;

import com.revature.models.ChatMessage;
import com.revature.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatService chatService;

    @MessageMapping("/message/{room}") // app/message
    @SendTo("/chatroom/{room}") // chatroom
    private ChatMessage receivePublicMessage(@Payload ChatMessage message , @PathVariable String room) {
        chatService.addChatRoom(room);
        return message;
    }

    @GetMapping("/chatrooms")
    private Set<String> getChatRooms() {
        return chatService.getChatRooms();
    }
}
