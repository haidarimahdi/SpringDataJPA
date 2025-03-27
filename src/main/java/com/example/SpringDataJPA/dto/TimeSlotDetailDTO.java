package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlotDetailDTO {
    private Integer id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer personId;

    public TimeSlotDetailDTO(TimeSlot timeSlot) {
        this.id = timeSlot.getId();
        this.date = timeSlot.getDate();
        this.startTime = timeSlot.getStartTime();
        this.endTime = timeSlot.getEndTime();
        this.personId = timeSlot.getPerson() != null ? timeSlot.getPerson().getId() : null;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getPersonId() {
        return personId;
    }
}
