package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
