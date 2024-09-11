package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Appointment;
import com.example.hospitalMenagment.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByDoctorAndAppointmentDataTimeBetween(Doctor doctor, LocalDateTime startOfDay, LocalDateTime endOfDay);


    Optional<Appointment> findByIdAndDoctor(Long appointmentId, Doctor doctor);
}
