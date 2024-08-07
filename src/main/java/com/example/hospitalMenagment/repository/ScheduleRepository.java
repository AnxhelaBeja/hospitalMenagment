package com.example.hospitalMenagment.repository;

import com.example.hospitalMenagment.model.Doctor;
import com.example.hospitalMenagment.model.DoctorProfile;
import com.example.hospitalMenagment.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByDoctorDoctorProfileAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(DoctorProfile profile, LocalDateTime dateTime, LocalDateTime dateTime1);
}
