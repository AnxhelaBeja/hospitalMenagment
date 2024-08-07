package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
