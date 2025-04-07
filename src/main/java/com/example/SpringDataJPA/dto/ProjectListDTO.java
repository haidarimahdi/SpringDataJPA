package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.Project;

/**
 * Data Transfer Object (DTO) for Project entity.
 * This class is used to transfer data between the service layer and the controller layer.
 * It contains the project's ID, name, description, scheduled effort,
 * total actual hours, and a flag indicating if the project is overbooked.
 */
public class ProjectListDTO {

    private final Integer id;
    private final String name;
    private final String description;
    private final Integer scheduledEffort;
    private final double totalActualHours;
    private final boolean isOverBooked;

    public ProjectListDTO(Project project, double totalActualHours) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.scheduledEffort = project.getScheduled_effort();
        this.totalActualHours = totalActualHours;

        // Calculate the flag
        if (this.scheduledEffort != null && this.scheduledEffort > 0) {
            this.isOverBooked = this.totalActualHours > this.scheduledEffort;
        } else {
            this.isOverBooked = false;
        }
    }

    // --- Getters ---
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getScheduledEffort() { return scheduledEffort; }
    public double getTotalActualHours() { return totalActualHours; }
    public boolean isOverBooked() { return isOverBooked; }

    public String getFormattedScheduledEffort() {
        return scheduledEffort != null ? scheduledEffort + " hours" : "No effort scheduled";
    }

    public String getFormattedActualHours() {
        return String.format("%.2f hours", totalActualHours);
    }
}