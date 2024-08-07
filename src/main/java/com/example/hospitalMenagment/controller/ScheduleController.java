package com.example.hospitalMenagment.controller;

import com.example.hospitalMenagment.model.DoctorProfile;
import com.example.hospitalMenagment.model.dto.AvailableDoctorResponse;
import com.example.hospitalMenagment.service.DoctorService;
import com.example.hospitalMenagment.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final DoctorService doctorService;

    public ScheduleController(ScheduleService scheduleService, DoctorService doctorService) {
        this.scheduleService = scheduleService;
        this.doctorService = doctorService;
    }
    @GetMapping("/available")
    public ResponseEntity<List<AvailableDoctorResponse>> getAvailableDoctors(
            @RequestParam DoctorProfile profile,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {

        List<AvailableDoctorResponse> availableDoctors = scheduleService.findAvailableDoctors(profile, dateTime);

        if (availableDoctors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList(
                    new AvailableDoctorResponse("No doctors available", profile, "N/A")
            ));
        }

        return ResponseEntity.ok(availableDoctors);
    }

}
