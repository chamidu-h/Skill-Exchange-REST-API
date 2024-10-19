package com.freelancer.demo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")

public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    
    @ElementCollection 
    private List<String> skills = new ArrayList<>();
    
    @ElementCollection
    private List<Long> offeredServices = new ArrayList<>(); // IDs of offered services
    
    @ElementCollection
    private List<Long> requestedServices = new ArrayList<>(); // IDs of requested services

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<Long> getOfferedServices() {
        return offeredServices;
    }

    public void setOfferedServices(List<Long> offeredServices) {
        this.offeredServices = offeredServices;
    }

    public List<Long> getRequestedServices() {
        return requestedServices;
    }

    public void setRequestedServices(List<Long> requestedServices) {
        this.requestedServices = requestedServices;
    }
}
