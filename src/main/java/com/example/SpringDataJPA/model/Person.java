package com.example.SpringDataJPA.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a Person entity in the database.
 * It contains fields for the person's ID, first name, last name,
 * and a set of time slots and projects associated with the person.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "p_id")
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToMany(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    Set<TimeSlot> timeSlots = new HashSet<>();

    @ManyToMany(
            mappedBy = "persons",
            fetch = FetchType.LAZY
    )
    private Set<Project> projects = new HashSet<>();

    protected Person() {
    }


    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addTimeSlot(TimeSlot timeslot) {
        if (timeSlots == null) {
            timeSlots = new HashSet<TimeSlot>();
        }
        timeSlots.add(timeslot);
        timeslot.setPerson(this);
    }

    public Set<TimeSlot> getTimeslots() {
        return timeSlots;
    }

    public void setProjects(Project project) {

        if (projects == null) {
            projects = new HashSet<Project>();
        }
        projects.add(project);
        project.getPersons().add(this);
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

}
