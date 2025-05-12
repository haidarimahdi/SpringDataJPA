package com.example.SpringDataJPA.dto;

import java.util.List;

/**
 * DTO for representing detailed information about a project.
 * This includes the project's ID, name, description, scheduled effort,
 * and a list of persons assigned to the project with their relevant timeslots using nested DTOs.
 * Also includes total hours booked and percentage of total project hours.
 */
public class ProjectDetailDTO {
    private final Integer id;
    private final String name;
    private final String description;
    private final Integer scheduledEffort;
    private final List<AssignedPersonDTO> assignedPersons; // Nested DTO for persons

    /**
     * Constructor for ProjectDetailDTO.
     *
     */
    public ProjectDetailDTO(Integer id, String name, String description, Integer scheduledEffort,
                            List<AssignedPersonDTO> assignedPersons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.scheduledEffort = scheduledEffort;
        this.assignedPersons = assignedPersons;
    }

    // --- Getters for ProjectDetailDTO ---
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getScheduledEffort() { return scheduledEffort; }
    public List<AssignedPersonDTO> getAssignedPersons() { return assignedPersons; }

    /**
     * Nested DTO for representing a person assigned to a project.
     * This includes the person's ID, first name, last name,
     * a list of their timeslots for this project, total hours booked,
     * and percentage of total hours booked.
     */
    public static class AssignedPersonDTO {
        private final Integer personId;
        private final String firstName;
        private final String lastName;
        private final List<TimeSlotDetailDTO> projectTimeSlots;
        private final double totalHoursBooked;
        private final double percentageOfTotalHours;

        public AssignedPersonDTO(Integer personId, String firstName, String lastName,
                                 List<TimeSlotDetailDTO> projectTimeSlots, double totalHoursBooked,
                                 double percentageOfTotalHours) {
            this.personId = personId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.projectTimeSlots = projectTimeSlots;
            this.totalHoursBooked = totalHoursBooked;
            this.percentageOfTotalHours = percentageOfTotalHours;

        }

        // --- Getters for AssignedPersonDTO ---
        public Integer getPersonId() { return personId; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getFullName() { return (firstName != null ? firstName : "") + (lastName != null ? " " + lastName : ""); }
        public List<TimeSlotDetailDTO> getProjectTimeSlots() { return projectTimeSlots; }
        public double getTotalHoursBooked() { return totalHoursBooked; }
        public double getPercentageOfTotalHours() { return percentageOfTotalHours; }
    }
}