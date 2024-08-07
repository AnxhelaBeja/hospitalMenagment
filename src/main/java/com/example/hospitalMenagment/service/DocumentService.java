package com.example.hospitalMenagment.service;


import com.example.hospitalMenagment.model.Document;
import com.example.hospitalMenagment.model.MedicalRecord;
import com.example.hospitalMenagment.repository.DocumentRepository;
import com.example.hospitalMenagment.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final Path rootLocation = Paths.get("uploaded-documents");

    public DocumentService(DocumentRepository documentRepository, MedicalRecordRepository medicalRecordRepository) {
        this.documentRepository = documentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }




    public Document uploadDocument(MultipartFile file, Long medicalRecordId, String description) {
        try {
            String fileName = file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
            file.transferTo(destinationFile);

            MedicalRecord medicalRecord = medicalRecordRepository.findById(medicalRecordId)
                    .orElseThrow(() -> new RuntimeException("Medical record not found"));

            Document document = new Document();
            document.setFilePath(destinationFile.toString());
            document.setDescription(description);
            document.setUploadedDate(LocalDateTime.now());
            document.setMedicalRecord(medicalRecord);

            return documentRepository.save(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}
