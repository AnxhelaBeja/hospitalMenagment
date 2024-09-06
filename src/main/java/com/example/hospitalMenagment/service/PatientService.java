package com.example.hospitalMenagment.service;


import com.example.hospitalMenagment.model.Patient;
import com.example.hospitalMenagment.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    public void updatePatientStatus(long patientId, boolean active) {
        Patient patient=patientRepository.findById(patientId)
                .orElseThrow(()->new RuntimeException("Patient not found"));
        patient.setActive(active);
        patientRepository.save(patient);
    }
}
