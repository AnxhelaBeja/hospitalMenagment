package com.example.hospitalMenagment.service;

import com.example.hospitalMenagment.model.Appointment;
import com.example.hospitalMenagment.model.Doctor;
import com.example.hospitalMenagment.model.Patient;
import com.example.hospitalMenagment.model.Schedule;
import com.example.hospitalMenagment.model.dto.AppointmentRequestDto;
import com.example.hospitalMenagment.repository.AppointmentRepository;
import com.example.hospitalMenagment.repository.DoctorRepository;
import com.example.hospitalMenagment.repository.PatientRepository;
import com.example.hospitalMenagment.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {


    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private  final EmailService emailService;
    private final ScheduleRepository scheduleRepository;

    public AppointmentService(DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, EmailService emailService, ScheduleRepository scheduleRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.emailService = emailService;
        this.scheduleRepository = scheduleRepository;
    }


    public String createAppointment(AppointmentRequestDto appointmentRequestDto) {
        List<Doctor> doctors = doctorRepository.findByFirstNameAndLastNameAndDoctorProfile(
                appointmentRequestDto.getDoctorFirstName(),
                appointmentRequestDto.getDoctorLastName(),
                appointmentRequestDto.getDoctorProfile()
        );

        if (doctors.isEmpty()) {
            throw new IllegalArgumentException("Doctor not found");
        }

        Doctor doctor = doctors.get(0);

        Optional<Patient> optionalPatient = patientRepository.findById(appointmentRequestDto.getPatientId());

        if (optionalPatient.isEmpty()) {
            throw new IllegalArgumentException("Patient not found");
        }

        Patient patient = optionalPatient.get();

        Appointment appointment = Appointment.builder()
                .appointmentDataTime(appointmentRequestDto.getStartTime())
                .status("Scheduled")
                .doctor(doctor)
                .patient(patient)
                .build();


        appointmentRepository.save(appointment);

        Schedule schedule = new Schedule();
        schedule.setDoctor(doctor);
        schedule.setStartTime(appointmentRequestDto.getStartTime());
        schedule.setEndTime(appointmentRequestDto.getStartTime().plusHours(1));
        schedule.setAvailable(false);

        scheduleRepository.save(schedule);
        String appointmentDetails = String.format(
                "Date and Time: %s\nDoctor: %s %s\nProfile: %s",
                appointment.getAppointmentDataTime(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getDoctorProfile()
        );

        emailService.sendAppointmentConfirmation(patient.getEmail(), appointmentDetails);

        return "Appointment has been scheduled and email has been sent.";
    }

    public String cancelAppointment(Long appointmentId, String doctorComment) {
        Optional<Appointment> optionalAppointment= appointmentRepository.findById(appointmentId);

        if(optionalAppointment.isEmpty()){
          throw new IllegalArgumentException("Appointment not found");


        }
        Appointment appointment= optionalAppointment.get();
        appointment.setStatus("Cancelled");
        appointmentRepository.save(appointment);

        String cancellationMessage=String.format(
                "Your appointment with doctor %s %s for %s has been cancelled. %s",
                appointment.getDoctor().getFirstName(),
                appointment.getDoctor().getLastName(),
                appointment.getAppointmentDataTime().toString(),
                doctorComment != null ? "Doctor's Comment: " + doctorComment : ""
        );
        emailService.sendAppointmentConfirmation(appointment.getPatient().getEmail(), cancellationMessage);
        return"The appointment was canceled and the patient was notified.";
    }

}
