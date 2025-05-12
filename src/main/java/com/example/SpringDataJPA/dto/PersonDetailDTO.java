package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.Person;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for the detailed view of a Person, including their time slots and projects.
 * This class is used to transfer data between the service layer and the controller layer.
 * It contains the person's ID, first name, last name,
 * a list of time slots, and a list of projects.
 */
public class PersonDetailDTO {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final List<TimeSlotDetailDTO> timeSlots;
    private final List<ProjectBasicInfoDTO> projects;

    public PersonDetailDTO(Integer id, String firstName, String lastName,
                           List<TimeSlotDetailDTO> timeSlots,
                           List<ProjectBasicInfoDTO> projects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeSlots = timeSlots;
        this.projects = projects;
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
    public List<ProjectBasicInfoDTO> getProjects() {
        return projects;
    }
}
