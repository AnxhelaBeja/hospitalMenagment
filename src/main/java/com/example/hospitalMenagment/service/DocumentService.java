package com.example.hospitalMenagment.service;


import com.example.hospitalMenagment.model.Document;
import com.example.hospitalMenagment.model.MedicalRecord;
import com.example.hospitalMenagment.repository.DocumentRepository;
import com.example.hospitalMenagment.repository.MedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
@Service
public class DocumentService {
    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final Path rootLocation = Paths.get("uploaded-documents");

    private final JavaMailSenderImpl mailSender;

    public DocumentService(DocumentRepository documentRepository, MedicalRecordRepository medicalRecordRepository, JavaMailSenderImpl mailSender) {
        this.documentRepository = documentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.mailSender = mailSender;
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

            Document savedDocument = documentRepository.save(document);
            sendEmailToPatient(medicalRecord, savedDocument);


            return documentRepository.save(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }


    private void sendEmailToPatient(MedicalRecord medicalRecord,Document document) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(medicalRecord.getPatient().getEmail());
        message.setSubject("New Document Uploaded");

//        String downloadLink = "http://yourserver.com/download/" + document.getId();

        message.setText(String.format(
                "Dear %s,\n\n" +
                        "Dr.%s with profile %s has uploaded a new document related to your medical record.\n\n"
//                +"Document Description: %s\n"
                +"Medical Record Description: %s\n"
//                +"You can download the document : %s\n"
                +"Best regards,\n"
                +"Your Medical Team",
                medicalRecord.getPatient().getFirstName(),
                medicalRecord.getDoctor().getLastName(),
                document.getDescription(),
                medicalRecord.getDescription(),
                document.getFilePath()
        ));
        mailSender.send(message);
    }

    public Document getDocumentById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with ID: " + documentId));
    }


    public void updateDocument(Long documentId, MultipartFile file, String description, Long doctorId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        MedicalRecord medicalRecord = document.getMedicalRecord();
        if (medicalRecord == null) {
            throw new RuntimeException("Medical record not found for document.");
        }

        if (!medicalRecord.getDoctor().getId().equals(doctorId)) {
            throw new RuntimeException("Doctor is not authorized to update this document.");
        }

        try {
            if (!file.isEmpty()) {
                Path destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
                file.transferTo(destinationFile);
                document.setFilePath(destinationFile.toString());
            }

            document.setDescription(description);
            documentRepository.save(document);
        } catch (IOException e) {
            log.error("Failed to update the document file. Document ID: {}, Description: {}", documentId, description, e);
            throw new RuntimeException("Failed to update the document", e);
        }
    }


    public void deleteDocument(Long documentId) {
        Document document = getDocumentById(documentId);

        documentRepository.delete(document);

        try {
            Path filePath = Paths.get(document.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete the file", e);
        }
    }
}
