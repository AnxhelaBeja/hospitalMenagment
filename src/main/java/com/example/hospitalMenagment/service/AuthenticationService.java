package com.example.hospitalMenagment.service;


import com.example.hospitalMenagment.model.*;
import com.example.hospitalMenagment.model.dto.LoginRequest;
import com.example.hospitalMenagment.model.dto.UserDoctorRegister;
import com.example.hospitalMenagment.model.dto.UserPatientRegister;
import com.example.hospitalMenagment.repository.DoctorRepository;
import com.example.hospitalMenagment.repository.PatientRepository;
import com.example.hospitalMenagment.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private  final DoctorRepository doctorRepository;
  private  final PatientRepository patientRepository;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }
    public AuthenticationResponse register(UserDoctorRegister request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.DOCTOR);
        user= userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setDoctorProfile(request.getDoctorProfile());
        doctor.setUser(user);

        doctorRepository.save(doctor);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(LoginRequest request)    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

       User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token= jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
    public AuthenticationResponse registerPatient(UserPatientRegister request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.PATIENT);
        user= userRepository.save(user);

        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setPhone(request.getPhone());
        patient.setBirthday(request.getBirthday());
        patient.setUser(user);
        patientRepository.save(patient);


        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
