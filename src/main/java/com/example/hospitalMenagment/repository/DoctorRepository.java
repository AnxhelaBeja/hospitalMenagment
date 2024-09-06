package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Doctor;
import com.example.hospitalMenagment.model.DoctorProfile;
import com.example.hospitalMenagment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    List<Doctor> findByFirstNameAndLastNameAndDoctorProfile(String doctorFirstName, String doctorLastName, DoctorProfile doctorProfile);

    List<Doctor> findByDoctorProfile(DoctorProfile profile);

    Doctor findByUser(User user);
    Optional<Doctor> findByUserUsername(String username);

}
