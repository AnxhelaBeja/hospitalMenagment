package com.example.hospitalMenagment.service;


import com.example.hospitalMenagment.model.Appointment;
import com.example.hospitalMenagment.model.Doctor;
import com.example.hospitalMenagment.repository.AppointmentRepository;
import com.example.hospitalMenagment.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyReportService {


    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final EmailService emailService;

    public DailyReportService(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, EmailService emailService) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void sendDailyReports(){
        LocalDateTime tomorrow=LocalDateTime.now().plusDays(1);
        LocalDateTime startOfDay=tomorrow.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay=tomorrow.withHour(23).withMinute(59).withSecond(59);

        List<Doctor> doctors= doctorRepository.findAll();

        for (Doctor doctor: doctors){
            List<Appointment>appointments=appointmentRepository.findAllByDoctorAndAppointmentDataTimeBetween(doctor, startOfDay,endOfDay);

            if (!appointments.isEmpty()){
                StringBuilder report= new StringBuilder();
                report.append("Dear Dr").append(doctor.getFirstName()).append(doctor.getLastName()).append(",\n\n")
                        .append("Here are your appointments for tomorrow:\n\n");

            for (Appointment appointment: appointments){
                report.append("Appointment with").append(appointment.getPatient().getFirstName()).append("")
                        .append(appointment.getPatient().getLastName())
                        .append("\nTime").append(appointment.getAppointmentDataTime())
                        .append("\nStatus").append(appointment.getStatus()).append("\n\n");

            }
            report.append("Best regards,\nYour Hospital Management System");
            emailService.sendAppointmentConfirmation(doctor.getEmail(), report.toString());
            }
        }
    }
}
