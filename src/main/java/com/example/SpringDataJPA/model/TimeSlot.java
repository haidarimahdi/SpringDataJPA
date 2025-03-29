package com.example.SpringDataJPA.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class TimeSlot {

    @Id
    @GeneratedValue
    private Integer id;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    protected TimeSlot() {
    }

    public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, String description, Person person) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String info() {
        return String.format("TimeSlot: %d %s %s %s %s %s%n", id, date, startTime, endTime, description);
    }

}
