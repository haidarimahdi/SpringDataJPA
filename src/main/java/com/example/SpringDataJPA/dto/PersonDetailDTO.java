package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonDetailDTO {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final List<TimeSlotDetailDTO> timeSlots;

    public PersonDetailDTO(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.timeSlots = person.getTimeslots().stream()
                .map(TimeSlotDetailDTO::new)
                .collect(Collectors.toList());
    }

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
}
