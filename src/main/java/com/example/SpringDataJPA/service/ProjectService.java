package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing Project entities.
 * This class provides methods to create, update, delete, and retrieve Project entities,
 * as well as to manage the relationships between Projects, Persons, and TimeSlots.
 * It also provides methods to calculate total actual hours for projects and to map entities to DTOs.
 *
 * This class is annotated with @Component to indicate that it is a Spring-managed bean.
 * It is also annotated with @Transactional to manage transactions automatically.
 */
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
     * Get all projects' basic information.
     * @return List of ProjectBasicInfoDTO containing project ID and name.
     */
    public List<ProjectBasicInfoDTO> getAllProjectsBasicInfo() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> new ProjectBasicInfoDTO(project.getId(), project.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Get all projects with their total actual hours calculated.
     * @return List of ProjectListDTO containing project details and total actual hours.
     */
    @Transactional(readOnly = true)
    public List<ProjectListDTO> getProjectOverviewData() {
        List<Project> projects = projectRepository.findAllWithTimeSlots();

        return projects.stream().map(project -> {
            double totalActualSeconds = calculateTotalActualSecondsForProject(project);
            double totalActualHours = totalActualSeconds / 3600.0;
            return new ProjectListDTO(project, totalActualHours);
        }).collect(Collectors.toList());
    }

    /**
     * Calculates the total actual seconds for a given project, considering only timeslots
     * from currently assigned persons.
     * @param project The project for which to calculate actual hours.
     * @return The total actual seconds from assigned persons.
     */
    private double calculateTotalActualSecondsForProject(Project project) {
        double totalActualSeconds = 0;
        if (project.getTimeSlots() == null || project.getPersons() == null || project.getPersons().isEmpty()) {
            return totalActualSeconds;
        }

        Set<Person> currentlyAssignedPersons = project.getPersons();

        for (TimeSlot ts : project.getTimeSlots()) {
            if(ts.getPerson() != null && currentlyAssignedPersons.contains(ts.getPerson())) {
                if (ts.getStartTime() != null && ts.getEndTime() != null) {
                    long durationSeconds = Duration.between(ts.getStartTime(), ts.getEndTime()).toSeconds();
                    if (durationSeconds > 0) {
                        totalActualSeconds += durationSeconds;
                    }
                }
            }
        }

        return totalActualSeconds;
    }

    /**
     * Calculates the total seconds each person spent on a specific project.
     * @param project The project entity.
     * @return A map where keys are person IDs and values are total seconds spent by that person on the project.
     */
    private Map<Integer, Long> calculateSecondsPerPersonForProject(Project project) {
        Map<Integer, Long> secondsPerPerson = new HashMap<>();
        if (project.getPersons() == null) {
            return secondsPerPerson;
        }

        for (Person person : project.getPersons()) {
            long personTotalSecondsOnProject = 0;
            if (person.getTimeslots() != null) {
                for (TimeSlot timeSlot : person.getTimeslots()) {
                    if (timeSlot.getProject() != null && timeSlot.getProject().getId().equals(project.getId()) &&
                            timeSlot.getStartTime() != null && timeSlot.getEndTime() != null) {
                        long durationSeconds = Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()).toSeconds();
                        if (durationSeconds > 0) {
                            personTotalSecondsOnProject += durationSeconds;
                        }
                    }
                }
            }
            secondsPerPerson.put(person.getId(), personTotalSecondsOnProject);
        }

        return secondsPerPerson;
    }

    /**
     * Maps a set of persons to a list of AssignedPersonDTOs for a project.
     * @param persons The set of persons assigned to the project.
     * @param secondsPerPersonForProject A map of person IDs to their total seconds on the project.
     * @param totalProjectActualHours The total actual hours for the entire project.
     * @param projectId The ID of the current project.
     * @return A list of AssignedPersonDTOs.
     */
    private List<ProjectDetailDTO.AssignedPersonDTO> mapPersonsToAssignedPersonDTOs(
            Set<Person> persons,
            Map<Integer, Long> secondsPerPersonForProject,
            double totalProjectActualHours,
            Integer scheduledEffortHours,
            Integer projectId) {

        if (persons == null) {
            return new ArrayList<>();
        }

        double percentageDenominatorHours = getPercentageDenominatorHours(totalProjectActualHours, scheduledEffortHours);

        return persons.stream().map(person -> {
            List<TimeSlotDetailDTO> relevantTimeSlots = person.getTimeslots().stream()
                    .filter(ts -> ts.getProject() != null && ts.getProject().getId().equals(projectId))
                    .map(TimeSlotDetailDTO::new)
                    .collect(Collectors.toList());

            long personActualSecondsOnProject = secondsPerPersonForProject.getOrDefault(person.getId(), 0L);
            double personActualHoursBookedOnProject = personActualSecondsOnProject / 3600.0;

            double percentageOfScheduledEffort = 0.0;
            if (totalProjectActualHours > 0) {
                percentageOfScheduledEffort = (personActualHoursBookedOnProject / percentageDenominatorHours) * 100.0;
            }

            return new ProjectDetailDTO.AssignedPersonDTO(
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    relevantTimeSlots,
                    personActualHoursBookedOnProject,
                    percentageOfScheduledEffort
            );
        }).collect(Collectors.toList());
    }

    /**
     * Get the denominator for percentage calculation.
     * If scheduled effort hours are valid, use them; otherwise, use total project actual hours.
     * @param totalProjectActualHours The total actual hours for the project.
     * @param scheduledEffortHours The scheduled effort hours for the project.
     * @return The denominator for percentage calculation.
     */
    private static double getPercentageDenominatorHours(double totalProjectActualHours, Integer scheduledEffortHours) {
        double percentageDenominatorHours;
        boolean validScheduledHoursExist = scheduledEffortHours != null && scheduledEffortHours > 0;

        if (validScheduledHoursExist) {
            boolean isOverbooked = totalProjectActualHours > scheduledEffortHours;
            if (isOverbooked) {
                percentageDenominatorHours = totalProjectActualHours;
            } else {
                percentageDenominatorHours = scheduledEffortHours.doubleValue();
            }
        } else {
            percentageDenominatorHours = totalProjectActualHours;
        }
        return percentageDenominatorHours;
    }

    /**
     * Get project details by ID using ProjectDetailDTO.
     * Handles fetching the project and its related persons and relevant timeslots.
     * Calculates total actual hours for each person and the project.
     *
     * @param id The ID of the project.
     * @return ProjectDetailDTO containing project details, assigned persons, and their time slots.
     */
    @Transactional(readOnly = true)
    public ProjectDetailDTO getProjectDetails(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));

        Map<Integer, Long> secondsPerPersonForProject = calculateSecondsPerPersonForProject(project);

        long totalProjectActualSeconds = secondsPerPersonForProject.values().stream().mapToLong(Long::longValue).sum();
        double totalProjectActualHours = totalProjectActualSeconds / 3600.0;

        // Map assinged persons to AssignedPersonDTO
        List<ProjectDetailDTO.AssignedPersonDTO> assignedPersonDTOs = mapPersonsToAssignedPersonDTOs(
                project.getPersons(),
                secondsPerPersonForProject,
                totalProjectActualHours,
                project.getScheduled_effort(),
                project.getId()
        );

        return new ProjectDetailDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getScheduled_effort(),
                assignedPersonDTOs
        );
    }

    /**
     * Create a new project from a DTO.
     * @return ProjectDetailDTO of the created project.
     */
    @Transactional
    public ProjectDetailDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project(projectDTO.getName(), projectDTO.getDescription(), projectDTO.getScheduledEffort());
        assignPersonsToProject(project, projectDTO.getPersonIds());

        Project savedProject = projectRepository.save(project);
        return getProjectDetails(savedProject.getId());
    }

    /**
     * Update an existing project.
     * Handles updating the project details and reassigning persons.
     *
     * @param id The ID of the project to update.
     * @param projectDTO The DTO containing the updated project details.
     * @return ProjectDetailDTO of the updated project.
     */
    @Transactional
    public ProjectDetailDTO updateProject(Integer id, ProjectDTO projectDTO) {
        Project project = projectRepository.findByIdWithPersons(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));

        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setScheduled_effort(projectDTO.getScheduledEffort());

        updateProjectPersonAssignment(project, projectDTO.getPersonIds() != null
                ? projectDTO.getPersonIds() : new HashSet<>());

        Project updatedProject = projectRepository.save(project);
        return getProjectDetails(updatedProject.getId());
    }

    /**
     * Updates the set of persons assigned to a project.
     * @param project The project to update.
     * @param newPersonIds The new set of person IDs to be assigned to the project.
     */
    private void updateProjectPersonAssignment(Project project, Set<Integer> newPersonIds) {
        Set<Integer> currentPersonIds = project.getPersons().stream()
                .map(Person::getId)
                .collect(Collectors.toSet());

        Set<Integer> personIdsToRemove = new HashSet<>(currentPersonIds);
        personIdsToRemove.removeAll(newPersonIds);

        if (!personIdsToRemove.isEmpty()) {
            project.getPersons().removeIf(person -> personIdsToRemove.contains(person.getId()));
        }

        Set<Integer> personIdsToAdd = new HashSet<>(newPersonIds);
        personIdsToAdd.removeAll(currentPersonIds);
        if (!personIdsToAdd.isEmpty()) {
            assignPersonsToProject(project, personIdsToAdd);
        }
    }

    /**
     * Delete a project by ID.
     * Handles removing associations in the join table.
     * TimeSlots associated might be deleted due to CascadeType.ALL on Project.
     */
    @Transactional
    public void deleteProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + id));

        projectRepository.delete(project);
    }

    /**
     * Assigns a list of persons (by ID) to a given project.
     * @param project The project to assign persons to.
     * @param personIds The set of person IDs to assign.
     * @throws IllegalArgumentException if any person ID is invalid.
     */
    private void assignPersonsToProject(Project project, Set<Integer> personIds) {
        if (personIds == null || personIds.isEmpty()) {
            return;
        }
        List<Person> personsToAssign = personRepository.findAllById(personIds);
        if (personsToAssign.size() != personIds.size()) {
            Set<Integer> foundIds = personsToAssign.stream().map(Person::getId).collect(Collectors.toSet());
            personIds.removeAll(foundIds);
            throw new IllegalArgumentException("Cannot assign persons. Invalid Person IDs provided: " + personIds);
        }
        personsToAssign.forEach(project::addPerson);
    }

    /**
     * Maps Project Entity to ProjectDTO.
     * This is used for updating projects.
     * @param project The Project entity to map.
     * @return A ProjectDTO containing the project's basic info and person IDs.
     */
    public ProjectDTO mapEntityToDto(Project project) {
        Set<Integer> personIds = project.getPersons().stream()
                .map(Person::getId)
                .collect(Collectors.toSet());
        return new ProjectDTO(project.getName(), project.getDescription(), project.getScheduled_effort(), personIds);
    }

    /**
     * Get a ProjectDTO for the update form.
     * @param projectId The ID of the project to update.
     * @return A ProjectDTO containing the project's basic info and person IDs.
     */
    @Transactional(readOnly = true)
    public ProjectDTO getProjectDtoForUpdateForm(Integer projectId) {
        Project project = projectRepository.findByIdWithPersons(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));
        Set<Integer> personIds = project.getPersons().stream()
                .map(Person::getId)
                .collect(Collectors.toSet());
        return new ProjectDTO(project.getName(), project.getDescription(), project.getScheduled_effort(), personIds);
    }
}