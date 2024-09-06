package com.example.hospitalMenagment.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name ="doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String phone;
    private String yearsOfExperience;
    @OneToOne(cascade = CascadeType.ALL)
    private Appointment appointment;
    @OneToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private DoctorProfile doctorProfile;
    @Column(nullable = false)
    private boolean active= true;

    public Doctor() {
    }

    public Doctor(Long id, String firstName, String lastName, String email, String yearsOfExperience, String phone, Appointment appointment, User user, DoctorProfile doctorProfile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
        this.phone = phone;
        this.appointment = appointment;
        this.user = user;
        this.doctorProfile = doctorProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DoctorProfile getDoctorProfile() {
        return doctorProfile;
    }

    public void setDoctorProfile(DoctorProfile doctorProfile) {
        this.doctorProfile = doctorProfile;
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
