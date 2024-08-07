package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Doctor;
import com.example.hospitalMenagment.model.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    List<Doctor> findByFirstNameAndLastNameAndDoctorProfile(String doctorFirstName, String doctorLastName, DoctorProfile doctorProfile);

    List<Doctor> findByDoctorProfile(DoctorProfile profile);
}
