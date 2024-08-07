package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
