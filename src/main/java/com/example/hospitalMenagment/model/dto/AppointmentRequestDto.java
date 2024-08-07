package com.example.hospitalMenagment.model.dto;

import com.example.hospitalMenagment.model.DoctorProfile;

import java.time.LocalDateTime;

public class AppointmentRequestDto {
    private LocalDateTime startTime;
    private String doctorFirstName;
    private String doctorLastName;
    private DoctorProfile doctorProfile;
    private Long patientId;


    public AppointmentRequestDto(LocalDateTime startTime, String doctorFirstName, String doctorLastName, DoctorProfile doctorProfile, Long patientId) {
        this.startTime = startTime;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.doctorProfile = doctorProfile;
        this.patientId = patientId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public DoctorProfile getDoctorProfile() {
        return doctorProfile;
    }

    public void setDoctorProfile(DoctorProfile doctorProfile) {
        this.doctorProfile = doctorProfile;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
