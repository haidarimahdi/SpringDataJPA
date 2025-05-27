package com.example.SpringDataJPA.model;

import jakarta.persistence.*;
import org.hibernate.grammars.hql.HqlParser;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class represents a TimeSlot entity in the database.
 * It contains fields for the time slot's ID, date, start time, end time,
 * description, associated person, and project.
 */
@Entity
public class TimeSlot {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    private String description;

    private Long durationInSeconds; // to store the calculation of duration when needed

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "p_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


    protected TimeSlot() {
    }

    public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, String description,
                    Person person, Project project) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.person = person;
        this.project = project;
        this.updateDuration(); // Calculate duration upon creation
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
        updateDuration(); // Update duration when start time changes
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        updateDuration(); // Update duration when end time changes
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(Long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Updates the durationInSeconds based on the startTime and endTime.
     * This method is called whenever the start or end time is set or changed.
     */
    private void updateDuration() {
        if (startTime != null && endTime != null) {
            this.durationInSeconds = startTime.isBefore(endTime)
                ? Duration.between(startTime, endTime).toSeconds()
                : 0L;
        } else {
            this.durationInSeconds = null;
        }
    }

}
