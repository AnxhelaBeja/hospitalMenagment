package com.example.hospitalMenagment.service;


import com.example.hospitalMenagment.model.MedicalRecord;
import com.example.hospitalMenagment.repository.MedicalRecordRepository;
import com.example.hospitalMenagment.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    public List<MedicalRecord> getMedicalRecordsForPatient(Long patientId) {
        return  medicalRecordRepository.findByPatientId(patientId);
    }

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }
}
