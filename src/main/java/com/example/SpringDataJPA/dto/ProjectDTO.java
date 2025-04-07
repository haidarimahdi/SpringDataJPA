package com.example.SpringDataJPA.dto;

import jakarta.validation.constraints.NotBlank; // Add validation if needed
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.HashSet;

/**
 * Data Transfer Object (DTO) for Project.
 * This class is used to transfer data between the application and the client.
 * It contains the project name, description, scheduled effort,
 * and a set of person IDs assigned to the project.
 */
public class ProjectDTO {
    @NotBlank(message = "Project name cannot be blank")
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String name;
    private String description;
    private Integer scheduledEffort;
    private Set<Integer> personIds = new HashSet<>();

    // --- Constructors ---
    public ProjectDTO() {}

    public ProjectDTO(String name, String description, Integer scheduledEffort, Set<Integer> personIds) {
        this.name = name;
        this.description = description;
        this.scheduledEffort = scheduledEffort;
        this.personIds = personIds != null ? personIds : new HashSet<>();
    }

    // --- Getters ---

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public Integer getScheduledEffort() {
        return scheduledEffort;
    }
    public Set<Integer> getPersonIds() {
        return personIds;
    }

    // --- Setters ---
    public void setDescription(String description) {
        this.description = description;
    }
    public void setScheduledEffort(Integer scheduledEffort) {
        this.scheduledEffort = scheduledEffort;
    }
    public void setPersonIds(Set<Integer> personIds) {
        this.personIds = personIds != null ? personIds : new HashSet<>();
    }
}