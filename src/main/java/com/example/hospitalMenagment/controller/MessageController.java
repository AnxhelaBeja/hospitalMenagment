package com.example.hospitalMenagment.controller;

import com.example.hospitalMenagment.model.Message;
import com.example.hospitalMenagment.model.User;
import com.example.hospitalMenagment.model.dto.MessageRequestDto;
import com.example.hospitalMenagment.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {


    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam Long receiverId,
            @RequestBody String content) {

        String token = authorizationHeader.substring(7);
        String senderUsername = messageService.getUsernameFromToken(token);

        User sender = messageService.findUserByUsername(senderUsername);

        Message sentMessage = messageService.sendMessage(sender.getId(), receiverId, content);

        return ResponseEntity.ok("SMS sent successfully");
    }


    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<Message>> getMessagesByConversation(@PathVariable Long conversationId) {
        List<Message> messages = messageService.getMessagesByConversation(conversationId);
        return ResponseEntity.ok(messages);
    }
}

