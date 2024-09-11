package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Patient;
import com.example.hospitalMenagment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUserUsername(String username);
    Patient findByUser(User user);

    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
