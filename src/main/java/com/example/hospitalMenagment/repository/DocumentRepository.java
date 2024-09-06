package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Document;
import com.example.hospitalMenagment.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByMedicalRecordId(Long medicalRecordId);
}
