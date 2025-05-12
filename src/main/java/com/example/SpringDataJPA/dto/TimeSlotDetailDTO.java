package com.example.SpringDataJPA.dto;

import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) for TimeSlot details.
 * This class is used to transfer data between the application and the client.
 */
public class TimeSlotDetailDTO {
    private Integer id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

    private Integer personId;
    private Integer projectId;

    private PersonDTO person;
    private ProjectBasicInfoDTO project;

    /**
     * Default constructor.
     */
    public TimeSlotDetailDTO() {
    }

    /**
     * Constructor to create a TimeSlotDetailDTO with specific fields.
     * @param id The ID of the time slot.
     * @param date The date of the time slot.
     * @param startTime The start time of the time slot.
     * @param endTime The end time of the time slot.
     * @param description The description of the time slot.
     * @param personId The ID of the person associated with the time slot.
     */
    public TimeSlotDetailDTO(Integer id, LocalDate date, LocalTime startTime, LocalTime endTime,
                             String description, Integer personId) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.personId = personId;
    }

    /**
     * Constructor to create a TimeSlotDetailDTO from a TimeSlot entity.
     * @param timeSlot The TimeSlot entity to be converted into a DTO.
     */
    public TimeSlotDetailDTO(TimeSlot timeSlot) {
        this.id = timeSlot.getId();
        this.date = timeSlot.getDate();
        this.startTime = timeSlot.getStartTime();
        this.endTime = timeSlot.getEndTime();
        this.description = timeSlot.getDescription();
        Person person = timeSlot.getPerson();
        if (person != null) {
            this.personId = person.getId();
            this.person = new PersonDTO(person.getFirstName(), person.getLastName());
        }

        Project project = timeSlot.getProject();
        if (project != null) {
            this.projectId = project.getId();
            this.project = new ProjectBasicInfoDTO(project.getId(), project.getName());
        }
    }

    // Getters
    public Integer getId() {
        return id;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public String getDescription() {
        return description;
    }
    public Integer getPersonId() {
        return personId;
    }
    public Integer getProjectId() {
        return projectId;
    }
    public PersonDTO getPerson() {
        return person;
    }
    public ProjectBasicInfoDTO getProject() {
        return project;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    public void setPerson(PersonDTO person) {
        this.person = person;
    }
    public void setProject(ProjectBasicInfoDTO project) {
        this.project = project;
    }

    public String getProjectName() {
        return project != null ? project.getName() : "";
    }
    public String getFormattedDuration() {
        if (startTime == null || endTime == null) {
            return "N/A";
        }
        try {
            Duration duration = Duration.between(startTime, endTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            return String.format("%d hours, %d minutes", hours, minutes);
        } catch (Exception e) {
            return "Calculation Error";
        }
    }


}
