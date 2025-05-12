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
     * Constructor for TimeSlotDTO.
     * This constructor is used to create a new TimeSlotDTO object with the specified parameters.
     * @param date date of the time slot
     * @param startTime start time of the time slot
     * @param endTime end time of the time slot
     * @param description description of the time slot
     * @param personId ID of the person associated with the time slot
     * @param projectId ID of the project associated with the time slot
     */
    public TimeSlotDTO(LocalDate date, LocalTime startTime, LocalTime endTime, String description,
                       Integer personId, Integer projectId) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.personId = personId;
        this.projectId = projectId;
    }
//    public TimeSlotDTO(TimeSlot timeSlot) {
//        this.timeSlot = timeSlot;
//        this.date = timeSlot.getDate();
//        this.startTime = timeSlot.getStartTime();
//        this.endTime = timeSlot.getEndTime();
//        this.description = timeSlot.getDescription();
//        this.personId = (timeSlot.getPerson() != null) ? timeSlot.getPerson().getId() : null;
//        this.projectId = (timeSlot.getProject() != null) ? timeSlot.getProject().getId() : null;
//    }

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

    // --- Setters ---
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
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
