package com.example.hospitalMenagment.service;


import com.example.hospitalMenagment.model.*;
import com.example.hospitalMenagment.model.dto.DocumentResponseDto;
import com.example.hospitalMenagment.model.dto.ReportUpdateRequest;
import com.example.hospitalMenagment.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final JwtService jwtService;
    private final DocumentRepository documentRepository;
    private final PatientService patientService;
    private final AppointmentRepository appointmentRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository, JwtService jwtService, DocumentRepository documentRepository, PatientService patientService, AppointmentRepository appointmentRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
        this.jwtService = jwtService;
        this.documentRepository = documentRepository;
        this.patientService = patientService;
        this.appointmentRepository = appointmentRepository;
    }

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public List<DocumentResponseDto> getDocumentsByPatientUsername(String username) {
    Patient patient = patientRepository.findByUserUsername(username)
            .orElseThrow(() -> new RuntimeException("Patient not found with username: " + username));

    List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientId(patient.getId());

    if (medicalRecords.isEmpty()) {
        throw new RuntimeException("Medical record not found for patient: " + patient.getId());
    }

    List<DocumentResponseDto> documents = medicalRecords.stream()
            .flatMap(medicalRecord -> documentRepository.findByMedicalRecordId(medicalRecord.getId()).stream())
            .map(doc -> new DocumentResponseDto(doc.getFilePath(), doc.getDescription()))
            .collect(Collectors.toList());

    if (documents.isEmpty()) {
        throw new RuntimeException("No documents found for patient: " + patient.getId());
    }

    return documents;
}





    public boolean updateDoctorReport(Doctor doctor, ReportUpdateRequest request) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findByIdAndDoctor(request.getAppointmentId(), doctor);
        if (!appointmentOpt.isPresent()) {
            return false;
        }

        Appointment appointment = appointmentOpt.get();
        appointment.setStatus(request.getStatus());
        appointment.setNotes(request.getNotes());

        appointmentRepository.save(appointment);

        return true;
    }


}

