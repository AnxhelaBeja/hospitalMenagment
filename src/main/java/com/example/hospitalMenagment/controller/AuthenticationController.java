package com.example.hospitalMenagment.controller;

import com.example.hospitalMenagment.model.User;
import com.example.hospitalMenagment.model.AuthenticationResponse;
import com.example.hospitalMenagment.model.dto.LoginRequest;
import com.example.hospitalMenagment.model.dto.UserDoctorRegister;
import com.example.hospitalMenagment.model.dto.UserPatientRegister;
import com.example.hospitalMenagment.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
 public class AuthenticationController {

    private  final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

  @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody UserDoctorRegister request) {
        return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request ){
        return ResponseEntity.ok(authService.authenticate(request));
  }

    @PostMapping("/registerPatient")
    public ResponseEntity<AuthenticationResponse> registerPatient(
            @RequestBody UserPatientRegister request
    ){
        return ResponseEntity.ok(authService.registerPatient(request));
    }

}
