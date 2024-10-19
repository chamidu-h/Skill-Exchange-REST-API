package com.freelancer.demo;

import jakarta.persistence.*;

@Entity

public class Service {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String skillType;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User provider;

    // Constructors, getters, and setters
    public Service() {}

    public Service(String title, String description, String skillType, User provider) {
        this.title = title;
        this.description = description;
        this.skillType = skillType;
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }
}
