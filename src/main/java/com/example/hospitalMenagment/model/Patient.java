package com.example.hospitalMenagment.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    @Column(nullable = false)
    private LocalDate birthday;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @OneToOne
    private User user;

    public Patient() {

    }

    public Patient(Long id, String firstName, String lastName, String email, String address, String phone, List<Appointment> appointments, LocalDate birthday, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.appointments = appointments;
        this.birthday = birthday;
        this.user = user;
    }
}
