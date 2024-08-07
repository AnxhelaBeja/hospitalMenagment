package com.example.hospitalMenagment.controller;

import com.example.hospitalMenagment.model.Document;
import com.example.hospitalMenagment.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<Document> uploadDocument(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("medicalRecordId") Long medicalRecordId,
                                                   @RequestParam("description") String description) {
        Document document = documentService.uploadDocument(file, medicalRecordId, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(document);
    }
}


