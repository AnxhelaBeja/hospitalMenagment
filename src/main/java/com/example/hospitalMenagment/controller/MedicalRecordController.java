package com.example.hospitalMenagment.controller;


import com.example.hospitalMenagment.model.Doctor;
import com.example.hospitalMenagment.model.MedicalRecord;
import com.example.hospitalMenagment.model.User;
import com.example.hospitalMenagment.model.dto.DocumentResponseDto;
import com.example.hospitalMenagment.model.dto.ReportUpdateRequest;
import com.example.hospitalMenagment.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medical_record")
public class MedicalRecordController {


    private final MedicalRecordService medicalRecordService;
    private final JwtService jwtService;
    private final DocumentService documentService;
    private final DoctorService doctorService;
    private final DailyReportService dailyReportService;
    private  final UserService userService;

    public MedicalRecordController(MedicalRecordService medicalRecordService, JwtService jwtService, DocumentService documentService, DoctorService doctorService, DailyReportService dailyReportService, UserService userService) {
        this.medicalRecordService = medicalRecordService;
        this.jwtService = jwtService;
        this.documentService = documentService;
        this.doctorService = doctorService;
        this.dailyReportService = dailyReportService;
        this.userService = userService;
    }

//    @PostMapping("/add_medical")
//    @PreAuthorize("hasRole('DOCTOR')")
//    public ResponseEntity<String> addMedicalRecord(
//            @RequestBody MedicalRecord medicalRecord) {
//        MedicalRecord savedRecord = medicalRecordService.addMedicalRecord(medicalRecord);
//        String message = String.format("Medical record with ID %d has been successfully created.", savedRecord.getId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(message);
//    }

    @PostMapping("/add_medical")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> addMedicalRecord(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody MedicalRecord medicalRecord) {

        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);

        Doctor doctor = doctorService.findByUsername(username);
        medicalRecord.setDoctor(doctor);

        MedicalRecord savedRecord = medicalRecordService.addMedicalRecord(medicalRecord);

        String fullName = doctor.getFirstName() + " " + doctor.getLastName();
        String message = String.format("Medical record with ID %d has been successfully created by Doctor %s.", savedRecord.getId(), fullName);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }



    @GetMapping("/my-documents")
    public ResponseEntity<List<DocumentResponseDto>> getDocumentsByPatientUsername(
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);

        List<DocumentResponseDto> documents = medicalRecordService.getDocumentsByPatientUsername(username);
        return ResponseEntity.ok(documents);
    }

    @PostMapping("/update_report")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> updateDailyReport(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ReportUpdateRequest reportUpdateRequest) {

        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);

        Optional<User> userOpt = userService.findByUsername(username);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = userOpt.get();

        Doctor doctor = doctorService.findByUser(user);
        if (doctor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }

        boolean updated = medicalRecordService.updateDoctorReport(doctor, reportUpdateRequest);

        if (updated) {
            return ResponseEntity.ok("Report updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update report.");
        }
    }

}


    




