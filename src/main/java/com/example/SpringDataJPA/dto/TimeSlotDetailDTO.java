package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) for TimeSlot details.
 * This class is used to transfer data between the application and the client.
 */
public class TimeSlotDetailDTO {
    private final Integer id;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String description;
    private final Integer personId;

    /**
     * Constructor to create a TimeSlotDetailDTO from a TimeSlot entity.
     * @param timeSlot The TimeSlot entity to be converted into a DTO.
     */
    public TimeSlotDetailDTO(TimeSlot timeSlot) {
        this.id = timeSlot.getId();
        this.date = timeSlot.getDate();
        this.startTime = timeSlot.getStartTime();
        this.endTime = timeSlot.getEndTime();
        this.description = timeSlot.getDescription();
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
    public String getDescription() {
        return description;
    }
    public Integer getPersonId() {
        return personId;
    }
}
