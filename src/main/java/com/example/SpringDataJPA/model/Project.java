package com.example.SpringDataJPA.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a Project entity in the database.
 * It contains fields for the project's ID, name, description,
 * scheduled effort, and a set of time slots and persons associated with the project.
 */
@Entity
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Integer scheduled_effort;    // in hours

    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<TimeSlot> timeSlots = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_project", // name of the intermediate table
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "p_id")
    )
    private Set<Person> persons = new HashSet<>();

    protected Project() {
    }

    public Project(String name, String description, Integer scheduled_effort) {
        this.name = name;
        this.description = description;
        this.scheduled_effort = scheduled_effort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScheduled_effort() {
        return this.scheduled_effort;
    }

    public void setScheduled_effort(Integer scheduled_effort) {
        this.scheduled_effort = scheduled_effort;
    }

    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
        person.getProjects().add(this); // Maintain both sides
    }

    // --- Helper methods for relationships ---

    public void removePerson(Person person) {
        this.persons.remove(person);
        person.getProjects().remove(this);
    }
}
