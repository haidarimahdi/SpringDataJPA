package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.ProjectDTO;
import com.example.SpringDataJPA.dto.ProjectDetailDTO;
import com.example.SpringDataJPA.dto.ProjectListDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.ProjectRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository; // Needed for cascade delete checks or complex queries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PersonRepository personRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, PersonRepository personRepository) {
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
    }

    /**
     * Get all projects with their total actual hours calculated.
     * This method fetches all projects and calculates the total actual hours
     * from their associated TimeSlots.
     * @return List of ProjectListDTO containing project details and total actual hours.
     */
    @Transactional(readOnly = true)
    public List<ProjectListDTO> getProjectOverviewData() {
        List<Project> projects = projectRepository.findAllWithTimeSlots();
        List<ProjectListDTO> projectDTOs = new ArrayList<>();

        for (Project project : projects) {
            double totalActualSeconds = 0;
            // Calculate total actual hours from the project's timeslots
            // Ensure timeslots are loaded (depends on fetch strategy)
            for (TimeSlot ts : project.getTimeSlots()) {
                if (ts.getStartTime() != null && ts.getEndTime() != null) {
                    long durationSeconds = Duration.between(ts.getStartTime(), ts.getEndTime()).toSeconds();
                    if(durationSeconds > 0) {
                        totalActualSeconds += durationSeconds;
                    }
                }
            }
            double totalActualHours = totalActualSeconds / 3600.0;
            projectDTOs.add(new ProjectListDTO(project, totalActualHours));
        }

        return projectDTOs;
    }

    /**
     * Get project details by ID using ProjectDetailDTO.
     * Handles fetching the project and its related persons and relevant timeslots.
     */
    @Transactional(readOnly = true)
    public ProjectDetailDTO getProjectDetails(Integer id) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()) {
            throw new IllegalArgumentException("Project not found with id: " + id);
        }

        Project project = projectOpt.get();
        return new ProjectDetailDTO(project);
    }

    /**
     * Create a new project from a DTO.
     */
    @Transactional
    public Project createProject(ProjectDTO projectDTO) {
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription(), projectDTO.getScheduledEffort());

        // Find and assign persons
        if (projectDTO.getPersonIds() != null && !projectDTO.getPersonIds().isEmpty()) {
            Set<Integer> requestedIds = projectDTO.getPersonIds();
            List<Person> assignedPersons = personRepository.findAllById(requestedIds);
            if (assignedPersons.size() != requestedIds.size()) {
                Set<Integer> foundIds = assignedPersons.stream().map(Person::getId).collect(Collectors.toSet());
                requestedIds.removeAll(foundIds); // Find the missing IDs
                // Log or throw an error about invalid IDs
                System.err.println("Error: Invalid Person IDs provided for project creation: " + requestedIds);
                throw new IllegalArgumentException("Cannot create project. Invalid Person IDs provided: " + requestedIds);
            }
            assignedPersons.forEach(project::addPerson);
        }

        return projectRepository.save(project);
    }

    /**
     * Update an existing project.
     */
    @Transactional
    public Project updateProject(Integer id, ProjectDTO projectDTO) {
        Optional<Project> projectOptional = projectRepository.findByIdWithPersons(id); // Fetch with persons for update
        if (projectOptional.isEmpty()) {
            throw new IllegalArgumentException("Project not found with id: " + id);
        }

        Project project = projectOptional.get();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setScheduled_effort(projectDTO.getScheduledEffort());

        return projectRepository.save(project);
    }

    /**
     * Delete a project by ID.
     * Handles removing associations in the join table.
     * TimeSlots associated might be deleted due to CascadeType.ALL on Project.
     */
    @Transactional
    public void deleteProject(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            projectRepository.delete(project);
        } else {
            throw new IllegalArgumentException("Project not found with id: " + id);
        }
    }

    /**
     * Assigns a Person to a Project.
     */
    @Transactional
    public void assignPersonToProject(Integer projectId, Integer personId) {
        Optional<Project> projectOpt = projectRepository.findByIdWithPersons(projectId);
        Optional<Person> personOpt = personRepository.findById(personId);

        if (projectOpt.isEmpty()) {
            throw new IllegalArgumentException("Project not found with id: " + projectId);
        }
        if (personOpt.isEmpty()) {
            throw new IllegalArgumentException("Person not found with id: " + personId);
        }

        Project project = projectOpt.get();
        Person person = personOpt.get();

        project.addPerson(person);
    }

    /**
     * Removes a Person from a Project.
     */
    @Transactional
    public void removePersonFromProject(Integer projectId, Integer personId) {
        Optional<Project> projectOpt = projectRepository.findByIdWithPersons(projectId);
        Optional<Person> personOpt = personRepository.findById(personId);

        if (projectOpt.isEmpty()) {
            throw new IllegalArgumentException("Project not found with id: " + projectId);
        }
        if (personOpt.isEmpty()) {
            // Decide if this is an error or just means nothing to remove
            // throw new IllegalArgumentException("Person not found with id: " + personId);
            return; // Or log a warning
        }


        Project project = projectOpt.get();
        Person person = personOpt.get();

        project.removePerson(person);
    }

    @Transactional(readOnly = true)
    public Map<Person, List<TimeSlot>> getTimeSlotsForProjectGroupedByPerson(Integer projectId) {
        Optional<Project> projectOpt = projectRepository.findByIdWithPersonsAndFilteredTimeSlots(projectId);
        if (projectOpt.isEmpty()) {
            throw new IllegalArgumentException("Project not found with id: " + projectId);
        }
        Project project = projectOpt.get();
        return project.getPersons().stream()
                .collect(Collectors.toMap(
                        person -> person,
                        person -> person.getTimeslots().stream()
                                .filter(timeSlot -> timeSlot.getProject().getId().equals(projectId))
                                .collect(Collectors.toList())
                ));
    }

    // --- Helper Mappers ---

    /**
     * Maps Project Entity to ProjectDTO (basic info + person IDs).
     */
    public ProjectDTO mapEntityToDto(Project project) {
        Set<Integer> personIds = project.getPersons().stream()
                .map(Person::getId)
                .collect(Collectors.toSet());
        return new ProjectDTO(project.getName(), project.getDescription(), project.getScheduled_effort(), personIds);
    }

    /**
     * Maps Project Entity to ProjectDTO (basic info, no person IDs - for lists).
     */
    private ProjectDTO mapEntityToBasicDto(Project project) {
        return new ProjectDTO(project.getName(), project.getDescription(), project.getScheduled_effort(), null); // No person IDs for list view
    }

}