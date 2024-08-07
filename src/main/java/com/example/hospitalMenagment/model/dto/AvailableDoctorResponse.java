package com.example.hospitalMenagment.model.dto;

import com.example.hospitalMenagment.model.DoctorProfile;

public class AvailableDoctorResponse {
    private String doctorName;
    private DoctorProfile doctorProfile;
    private String yearsOfExperience;


    public AvailableDoctorResponse(String doctorName, DoctorProfile doctorProfile, String yearsOfExperience) {
        this.doctorName = doctorName;
        this.doctorProfile = doctorProfile;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public DoctorProfile getDoctorProfile() {
        return doctorProfile;
    }

    public void setDoctorProfile(DoctorProfile doctorProfile) {
        this.doctorProfile = doctorProfile;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
