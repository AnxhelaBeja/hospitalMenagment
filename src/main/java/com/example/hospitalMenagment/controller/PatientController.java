package com.example.hospitalMenagment.controller;


import com.example.hospitalMenagment.service.JwtService;
import com.example.hospitalMenagment.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
public class PatientController {
    private final JwtService jwtService;
    private final PatientService patientService;

    public PatientController(JwtService jwtService, PatientService patientService) {
        this.jwtService = jwtService;
        this.patientService = patientService;
    }
    @PostMapping("/update_status")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> updatePatientStatus(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("patientId") Long patientId,
            @RequestParam("active") boolean active) {

        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);


        patientService.updatePatientStatus(patientId, active);

        return ResponseEntity.ok("Patient status updated successfully.");
    }
}
