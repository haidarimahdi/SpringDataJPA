package com.example.SpringDataJPA.dto;

import java.util.List;

/**
 * DTO for representing detailed information about a project.
 * This includes the project's ID, name, description, scheduled effort,
 * and a list of persons assigned to the project with their relevant timeslots using nested DTOs.
 * Also includes total hours booked and percentage of total project hours.
 *
 * @param assignedPersons Nested DTO for persons
 */
public record ProjectDetailDTO(Integer id, String name, String description, Integer scheduledEffort,
                               List<AssignedPersonDTO> assignedPersons) {
    /**
     * Constructor for ProjectDetailDTO.
     */
    public ProjectDetailDTO {
    }

    /**
         * Nested DTO for representing a person assigned to a project.
         * This includes the person's ID, first name, last name,
         * a list of their timeslots for this project, total hours booked,
         * and percentage of total hours booked.
         */
        public record AssignedPersonDTO(Integer personId, String firstName, String lastName,
                                        List<TimeSlotDetailDTO> projectTimeSlots, double totalHoursBooked,
                                        double percentageOfTotalHours) {

        public String getFullName() {
                return (firstName != null ? firstName : "") + (lastName != null ? " " + lastName : "");
            }
        }
}