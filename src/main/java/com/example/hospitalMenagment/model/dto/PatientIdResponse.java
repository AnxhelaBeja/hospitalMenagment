package com.example.hospitalMenagment.model.dto;

public class PatientIdResponse {
    private Long id;

    public PatientIdResponse() {
    }

    public PatientIdResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

