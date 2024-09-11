package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Conversation;
import com.example.hospitalMenagment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findBySenderAndReceiver(User sender, User receiver);

    Optional<Conversation> findByReceiverAndSender(User receiver, User sender);
}
