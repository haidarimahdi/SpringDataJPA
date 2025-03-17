package com.example.SpringDataJPA.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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
            fetch = FetchType.EAGER
    )
    Set<TimeSlot> timeSlots;

    public Person() {
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

    public void addTimeslot(TimeSlot timeslot) {
        if (timeSlots == null) {
            timeSlots = new HashSet<TimeSlot>();
        }
        timeSlots.add(timeslot);
    }

    public Set<TimeSlot> getTimeslots() {
        return timeSlots;
    }
}
