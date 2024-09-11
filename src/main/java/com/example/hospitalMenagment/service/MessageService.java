package com.example.hospitalMenagment.service;


import com.example.hospitalMenagment.model.*;
import com.example.hospitalMenagment.repository.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final EmailService emailService;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final JwtService jwtService;

    public MessageService(UserRepository userRepository, ConversationRepository conversationRepository, MessageRepository messageRepository, EmailService emailService, DoctorRepository doctorRepository, PatientRepository patientRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.emailService = emailService;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.jwtService = jwtService;
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        Conversation conversation = conversationRepository.findBySenderAndReceiver(sender, receiver)
                .orElseGet(() -> createConversation(sender, receiver));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());
        message.setConversation(conversation);

        Message savedMessage = messageRepository.save(message);

        sendNotificationEmail(sender, receiver, content);

        return savedMessage;
    }


    private void sendNotificationEmail(User sender, User receiver, String content) {
        String subject = "New Message Notification";
        String text = String.format("You have a new message from %s: %s", sender.getUsername(), content);

        String receiverEmail = null;

        Doctor doctor = doctorRepository.findByUser(receiver);
        Patient patient = patientRepository.findByUser(receiver);

        if (doctor != null) {
            receiverEmail = doctor.getEmail();
        } else if (patient != null) {
            receiverEmail = patient.getEmail();
        } else {
            throw new IllegalArgumentException("Receiver type is not recognized.");
        }

        if (receiverEmail != null) {
            emailService.sendEmail(receiverEmail, subject, text);
        } else {
            throw new IllegalArgumentException("No email address found for the receiver.");
        }
    }

public Conversation createConversation(User sender, User receiver) {
        Optional<Conversation> conversationOpt = conversationRepository.findBySenderAndReceiver(sender, receiver);

        if (!conversationOpt.isPresent()) {
            conversationOpt = conversationRepository.findByReceiverAndSender(receiver, sender);
        }

        if (!conversationOpt.isPresent()) {
            Conversation conversation = new Conversation();
            conversation.setSender(sender);
            conversation.setReceiver(receiver);
            return conversationRepository.save(conversation);
        }

        return conversationOpt.get();
    }

    public List<Message> getMessagesByConversation(Long conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }

    public String getUsernameFromToken(String token) {
        return jwtService.extractUsername(token);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }}










