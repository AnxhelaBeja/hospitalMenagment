package com.example.hospitalMenagment.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
@Entity
@Table(name= "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String content;
    private LocalDateTime sentAt;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public Message() {

    }

    public Message(Long id, String content, LocalDateTime sentAt, User sender, User receiver, Conversation conversation) {
        this.id = id;
        this.content = content;
        this.sentAt = sentAt;
        this.sender = sender;
        this.receiver = receiver;
        this.conversation = conversation;
    }
}
