package com.example.hospitalMenagment.service;

import com.example.hospitalMenagment.model.Doctor;
import com.example.hospitalMenagment.model.User;
import com.example.hospitalMenagment.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    public Doctor findByUser(User user) {
        return doctorRepository.findByUser(user);
    }
    public Doctor findByUsername(String username) {
        return doctorRepository.findByUserUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Doctor not found with username: " + username));
    }
    public void updateDoctorStatus(Long doctorId, boolean active) {
        Doctor doctor= doctorRepository.findById(doctorId)
                .orElseThrow(() -> new UsernameNotFoundException("Doctor not found with id: " + doctorId));
        doctor.setActive(active);
        doctorRepository.save(doctor);
    }
}
