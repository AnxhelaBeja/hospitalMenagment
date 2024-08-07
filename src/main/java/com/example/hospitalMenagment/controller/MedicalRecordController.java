package com.example.hospitalMenagment.controller;


import com.example.hospitalMenagment.model.MedicalRecord;
import com.example.hospitalMenagment.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical_record")
public class MedicalRecordController {


    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }


    @GetMapping("/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecords(@PathVariable Long patientId) {
        List<MedicalRecord> records = medicalRecordService.getMedicalRecordsForPatient(patientId);
        return ResponseEntity.ok(records);
    }

    @PostMapping("/add_medical")
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        MedicalRecord savedRecord = medicalRecordService.addMedicalRecord(medicalRecord);
        String message = String.format("Medical record with ID %d has been successfully created.", savedRecord.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


}
