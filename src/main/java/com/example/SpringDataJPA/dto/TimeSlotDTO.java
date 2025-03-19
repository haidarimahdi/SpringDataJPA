package com.example.SpringDataJPA.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlotDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private Integer personId;

    public TimeSlotDTO() {
    }

    public TimeSlotDTO(LocalDate date, LocalTime startTime, LocalTime endTime, String description, Integer personId) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.personId = personId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
