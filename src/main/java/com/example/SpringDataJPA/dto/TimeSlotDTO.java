package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.TimeSlot;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) for TimeSlot.
 * This class is used to transfer data between the application and the client.
 */
public class TimeSlotDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date is required")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    @NotNull(message = "End time is required")
    private LocalTime endTime;

    private String description;

    private Integer personId;

    private Integer projectId;

    private TimeSlot timeSlot;

    public TimeSlotDTO() {
    }

    /**
     * Constructor to create a TimeSlotDTO from a TimeSlot entity.
     * @param timeSlot The TimeSlot entity to be converted into a DTO.
     */
    public TimeSlotDTO(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
        this.date = timeSlot.getDate();
        this.startTime = timeSlot.getStartTime();
        this.endTime = timeSlot.getEndTime();
        this.description = timeSlot.getDescription();
        this.personId = (timeSlot.getPerson() != null) ? timeSlot.getPerson().getId() : null;
        this.projectId = (timeSlot.getProject() != null) ? timeSlot.getProject().getId() : null;
    }

    // --- Getters ---
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public String getDescription() {
        return description;
    }
    public Integer getPersonId() {
        return personId;
    }
    public Integer getProjectId() {
        return projectId;
    }
    public String getFullName() {
        return String.format("%s %s", timeSlot.getPerson().getFirstName(), timeSlot.getPerson().getLastName());
    }

    public String getProjectName() {
        return timeSlot.getProject() != null ? timeSlot.getProject().getName() : null;
    }

    // --- Setters ---
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
