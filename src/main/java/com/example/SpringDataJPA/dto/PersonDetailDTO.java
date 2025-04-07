package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.Person;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for detailed view of a Person, including their time slots and projects.
 * This class is used to transfer data between the service layer and the controller layer.
 * It contains the person's ID, first name, last name,
 * a list of time slots, and a list of projects.
 */
public class PersonDetailDTO {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final List<TimeSlotDetailDTO> timeSlots;
    private final List<ProjectDetailDTO> projects;

    public PersonDetailDTO(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.timeSlots = person.getTimeslots().stream()
                .map(TimeSlotDetailDTO::new)
                .collect(Collectors.toList());
        this.projects = person.getProjects().stream()
                .map(ProjectDetailDTO::new)
                .collect(Collectors.toList());
    }

    // Getters
    public Integer getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public List<TimeSlotDetailDTO> getTimeSlots() {
        return timeSlots;
    }
    public List<ProjectDetailDTO> getProjects() {
        return projects;
    }
}
