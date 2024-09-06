package com.example.hospitalMenagment.controller;


import com.example.hospitalMenagment.service.DoctorService;
import com.example.hospitalMenagment.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final JwtService jwtService;
    private final DoctorService doctorService;

    public DoctorController(JwtService jwtService, DoctorService doctorService) {
        this.jwtService = jwtService;
        this.doctorService = doctorService;
    }

    @PostMapping("/update_status")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> updateDoctorStatus(
            @RequestHeader ("Authorization") String authorizationHeader ,
            @RequestParam("doctorId") Long doctorId,
            @RequestParam ("active") boolean active
    ) {
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);
        doctorService.updateDoctorStatus(doctorId,active);
        return ResponseEntity.ok("Doctor status updated successfully.");
    }
}
