package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.MedicalRecord;
import com.example.hospitalMenagment.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findById(Long id);
    List<MedicalRecord> findByPatientId(Long patientId);


//    Optional<MedicalRecord> findByPatientId(Long patientId);
}
