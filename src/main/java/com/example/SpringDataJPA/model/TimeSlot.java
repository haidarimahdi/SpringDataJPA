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

    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    @Transient
    public String getFormattedDuration() {
        try {
            Duration duration = Duration.between(startTime, endTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();

            return String.format("%d hours, %d minutes", hours, minutes);
        } catch (Exception e) {
            return "Calculation Error on the duration of the time slot";
        }
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", personId=" + (person != null ? person.getId() : null) +
                ", projectId=" + (project != null ? project.getId() : null) +
                '}';
    }

}
