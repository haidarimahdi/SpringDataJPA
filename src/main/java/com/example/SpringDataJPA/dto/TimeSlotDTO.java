package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.TimeSlot;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlotDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private String description;
    private Integer personId;
    private TimeSlot timeSlot;
    private String fulllName;

    public TimeSlotDTO() {
    }

    public TimeSlotDTO(LocalDate date, LocalTime startTime, LocalTime endTime, String description, Integer personId) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.personId = personId;
    }

    public TimeSlotDTO(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
        this.date = timeSlot.getDate();
        this.startTime = timeSlot.getStartTime();
        this.endTime = timeSlot.getEndTime();
        this.description = timeSlot.getDescription();
        this.personId = timeSlot.getPerson().getId();
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

    public String getFullName() {
        return String.format("%s %s", timeSlot.getPerson().getFirstName(), timeSlot.getPerson().getLastName());
    }
}
