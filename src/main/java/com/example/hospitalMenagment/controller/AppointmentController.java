package com.example.hospitalMenagment.controller;

import com.example.hospitalMenagment.model.Appointment;
import com.example.hospitalMenagment.model.dto.AppointmentRequestDto;
import com.example.hospitalMenagment.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {


    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/add")
    public String createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto) {
        return appointmentService.createAppointment(appointmentRequestDto);
    }
    @PostMapping("/cancel")
    public String cancelAppointment(
            @RequestParam Long appointmentId,
            @RequestParam (required = false) String doctorComment) {
        return appointmentService.cancelAppointment(appointmentId, doctorComment);

    }

}
