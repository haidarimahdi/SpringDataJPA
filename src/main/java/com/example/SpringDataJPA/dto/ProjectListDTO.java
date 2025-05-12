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
    private final double totalActualHours;
    private final boolean isOverBooked;
    private final String formattedScheduledEffort;
    private final String formattedActualHours;

    public ProjectListDTO(Project project, double totalActualHours) {
        this.id = project.getId();
        this.name = project.getName();
        this.totalActualHours = totalActualHours;
        Integer scheduledEffortEntity = project.getScheduled_effort();

        if (scheduledEffortEntity != null && scheduledEffortEntity > 0) {
            this.isOverBooked = this.totalActualHours > scheduledEffortEntity;
        } else {
            this.isOverBooked = false;
        }

        this.formattedScheduledEffort = scheduledEffortEntity != null ? scheduledEffortEntity + " hours" :
                "No effort scheduled";
        this.formattedActualHours = String.format("%.2f hours", totalActualHours);

    }

    // --- Getters ---
    public Integer getId() { return id; }
    public String getName() { return name; }
    public double getTotalActualHours() { return totalActualHours; }
    public boolean isOverBooked() { return isOverBooked; }

    public String getFormattedScheduledEffort() {
        return formattedScheduledEffort;
    }

    public String getFormattedActualHours() {
        return formattedActualHours;
    }
}