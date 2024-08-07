package com.example.hospitalMenagment.service;

import com.example.hospitalMenagment.model.Doctor;
import com.example.hospitalMenagment.model.DoctorProfile;
import com.example.hospitalMenagment.model.Schedule;
import com.example.hospitalMenagment.model.dto.AvailableDoctorResponse;
import com.example.hospitalMenagment.repository.AppointmentRepository;
import com.example.hospitalMenagment.repository.DoctorRepository;
import com.example.hospitalMenagment.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {


    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<AvailableDoctorResponse> findAvailableDoctors(DoctorProfile profile, LocalDateTime dateTime) {
        List<Schedule> schedules = scheduleRepository.findByDoctorDoctorProfileAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(profile, dateTime, dateTime);

        List<AvailableDoctorResponse> response = new ArrayList<>();

        List<Doctor> doctors = doctorRepository.findByDoctorProfile(profile);

        for (Doctor doctor : doctors) {
            boolean isAvailable = true;

            for (Schedule schedule : schedules) {
                if (schedule.getDoctor().equals(doctor)) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                response.add(new AvailableDoctorResponse(
                        doctor.getFirstName() + " " + doctor.getLastName(),
                        doctor.getDoctorProfile(),
                        doctor.getYearsOfExperience()
                ));
            }
        }

        return response;
    }




//    public List<AvailableDoctorResponse> findAvailableDot(DoctorProfile profile, LocalDateTime dateTime) {
//        List<Schedule> schedules = scheduleRepository.findByDoctorDoctorProfileAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndIsAvailableTrue(profile, dateTime, dateTime);
//
//        List<AvailableDoctorResponse> response = new ArrayList<>();
//
//        for (Schedule schedule : schedules) {
//            Doctor doctor = schedule.getDoctor();
//            response.add(new AvailableDoctorResponse(
//                    doctor.getFirstName() + " " + doctor.getLastName(),
//                    doctor.getDoctorProfile(),
//                    doctor.getYearsOfExperience()
//            ));
//        }
//
//        return response;
//    }

//    public List<Doctor> findAvailableDoctors(LocalDate date, LocalTime time, DoctorProfile profile) {
//        LocalDateTime startDateTime = LocalDateTime.of(date, time);
//        LocalDateTime endDateTime = startDateTime.plusHours(1);
//
//        List<Schedule> schedules = scheduleRepository.findByDoctor_DoctorProfileAndStartTimeBetween(profile, startDateTime, endDateTime);
//        List<Doctor> availableDoctors = new ArrayList<>();
//
//        for (Schedule schedule : schedules) {
//            if (schedule.isAvailable()) {
//                availableDoctors.add(schedule.getDoctor());
//            }
//        }
//
//        return availableDoctors;
//    }

}
