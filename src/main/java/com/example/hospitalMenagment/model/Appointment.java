package com.example.hospitalMenagment.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Entity
@Data
@Table(name = "appointments")

public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime  appointmentDataTime;
    private String status;
    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment() {

    }

    public Appointment(Long id, LocalDateTime appointmentDataTime, String status, Doctor doctor, Patient patient) {
        this.id = id;
        this.appointmentDataTime = appointmentDataTime;
        this.status = status;
        this.doctor = doctor;
        this.patient = patient;
    }
}
