package com.example.hospitalMenagment.controller;

import com.example.hospitalMenagment.model.Doctor;
import com.example.hospitalMenagment.model.Document;
import com.example.hospitalMenagment.model.MedicalRecord;
import com.example.hospitalMenagment.repository.DocumentRepository;
import com.example.hospitalMenagment.repository.MedicalRecordRepository;
import com.example.hospitalMenagment.service.DoctorService;
import com.example.hospitalMenagment.service.DocumentService;
import com.example.hospitalMenagment.service.JwtService;
import com.example.hospitalMenagment.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final Path rootLocation = Paths.get("C:/Users/YourUsername/Desktop/MedicalFiles");
    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);


    private final DocumentService documentService;
    private final MedicalRecordRepository medicalRecordRepository;
    private final DocumentRepository documentRepository;
    private final JwtService jwtService;
    private final DoctorService doctorService;

    public DocumentController(DocumentService documentService, MedicalRecordRepository medicalRecordRepository, DocumentRepository documentRepository, JwtService jwtService, DoctorService doctorService) {
        this.documentService = documentService;
        this.medicalRecordRepository = medicalRecordRepository;
        this.documentRepository = documentRepository;
        this.jwtService = jwtService;
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("medicalRecordId") Long medicalRecordId,
                                                 @RequestParam("description") String description) {
        documentService.uploadDocument(file, medicalRecordId, description);
        return ResponseEntity.status(HttpStatus.CREATED).body("File was downloaded successfully!");
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) {
        Document document = documentService.getDocumentById(documentId);
        Path filePath = Paths.get(document.getFilePath());
        Resource resource = new FileSystemResource(filePath.toFile());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/update_document")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> updateDocument(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("documentId") Long documentId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) {

        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Doctor doctor = doctorService.findByUsername(username);

        try {
            documentService.updateDocument(documentId, file, description, doctor.getId());
            return ResponseEntity.ok("Document updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the document.");
        }
    }

  @DeleteMapping("/delete_document/{documentId}")
    @PreAuthorize("haseROLE('DOCTOR')")
  public ResponseEntity<String> deleteDocument(
          @RequestHeader("Authorization") String authorizationHeader,
          @PathVariable Long documentId) {

      String token = authorizationHeader.substring(7);
      String username = jwtService.extractUsername(token);

    try {
        documentService.deleteDocument(documentId);
        return ResponseEntity.ok("Document deleted successfully.");
    }catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }

}


