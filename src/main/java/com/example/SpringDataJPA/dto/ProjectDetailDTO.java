package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.TimeSlot;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * Constructor to create a ProjectDetailDTO from a Project entity.
     * This constructor initializes the project details and calculates the total hours booked
     * and percentage of total project hours for each assigned person.
     *
     * @param project The Project entity to be converted into a DTO.
     */
    public ProjectDetailDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.scheduledEffort = project.getScheduled_effort();
        Map<Integer, Long> secondsPerPerson = new HashMap<>();

        for (Person person : project.getPersons()) {
            long personTotalSeconds = 0;
            for (TimeSlot timeSlot : person.getTimeslots()) {
                if (timeSlot.getProject() != null && timeSlot.getProject().getId().equals(project.getId()) &&
                        timeSlot.getStartTime() != null && timeSlot.getEndTime() != null) {
                    long durationSeconds = Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()).toSeconds();
                    if (durationSeconds > 0) {
                        personTotalSeconds += durationSeconds;
                    }
                }
            }
            if (personTotalSeconds > 0) {
                secondsPerPerson.put(person.getId(), personTotalSeconds);
            }
        }


        Integer scheduledEffortHours = project.getScheduled_effort();

        // Map assigned persons and their *relevant* timeslots
        this.assignedPersons = project.getPersons().stream()
                .map(person -> {
                    // Filter timeslots for this person that are relevant to the project
                    List<TimeSlotDetailDTO> relevantTimeSlots = person.getTimeslots().stream()
                            .filter(ts -> ts.getProject() != null &&
                                    ts.getProject().getId().equals(project.getId()))
                            .map(TimeSlotDetailDTO::new)
                            .collect(Collectors.toList());


                    long personActualSeconds = secondsPerPerson.getOrDefault(person.getId(), 0L);
                    double totalActualHoursBooked = personActualSeconds / 3600.0;

                    // 2. Calculate NEW percentage based on SCHEDULED effort
                    double percentageBasedOnScheduled = calculatePercentageBasedOnScheduled(
                            person, project.getId(), scheduledEffortHours);
                    return new AssignedPersonDTO(person, relevantTimeSlots, totalActualHoursBooked, percentageBasedOnScheduled);
                })
                .collect(Collectors.toList());
    }

    /**
     * Calculate the percentage of scheduled effort based on the person's timeslots.
     * This method iterates through the person's timeslots and calculates the percentage
     * of total hours booked against the scheduled effort.
     *
     * @param person               The person whose timeslots are being evaluated.
     * @param projectId            The ID of the project to which the timeslots belong.
     * @param scheduledEffortHours The scheduled effort in hours for the project.
     * @return The calculated percentage based on scheduled effort.
     */
    private double calculatePercentageBasedOnScheduled(Person person, Integer projectId, Integer scheduledEffortHours) {
        if (scheduledEffortHours == null || scheduledEffortHours <= 0) {
            return 0.0;
        }

        double scheduledEffortInHours = scheduledEffortHours.doubleValue();
        double percentageBasedOnScheduled = 0.0;

        for (TimeSlot ts : person.getTimeslots()) {
            if (ts.getProject() != null && ts.getProject().getId().equals(projectId) &&
                    ts.getStartTime() != null && ts.getEndTime() != null) {
                long durationSeconds = Duration.between(ts.getStartTime(), ts.getEndTime()).toSeconds();
                if (durationSeconds > 0) {
                    double durationHours = durationSeconds / 3600.0;
                    percentageBasedOnScheduled += (durationHours / scheduledEffortInHours) * 100.0;
                }
            }
        }
        return percentageBasedOnScheduled;
    }

    // --- Getters for ProjectDetailDTO ---
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getScheduledEffort() { return scheduledEffort; }
    public List<AssignedPersonDTO> getAssignedPersons() { return assignedPersons; }

    /**
     * Nested DTO for representing a person assigned to a project.
     * This includes the person's ID, name, and their relevant timeslots.
     * Also includes total hours booked and percentage of total project hours.
     */
    public static class AssignedPersonDTO {
        private final Integer personId;
        private final String firstName;
        private final String lastName;
        private final List<TimeSlotDetailDTO> projectTimeSlots; // TimeSlots specific to the parent Project
        private final double totalHoursBooked;
        private final double percentageOfTotalHours;

        public AssignedPersonDTO(Person person, List<TimeSlotDetailDTO> projectTimeSlots, double totalHoursBooked,
                                 double percentageOfTotalHours) {
            this.personId = person.getId();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.projectTimeSlots = projectTimeSlots;
            this.totalHoursBooked = totalHoursBooked;
            this.percentageOfTotalHours = percentageOfTotalHours;
        }

        // --- Getters for AssignedPersonDTO ---
        public Integer getPersonId() { return personId; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public List<TimeSlotDetailDTO> getProjectTimeSlots() { return projectTimeSlots; }
        public double getTotalHoursBooked() { return totalHoursBooked; }
        public double getPercentageOfTotalHours() { return percentageOfTotalHours; }
    }
}