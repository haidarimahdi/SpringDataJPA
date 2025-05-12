package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing Person entities.
 * This class provides methods to create, update, delete, and retrieve Person entities,
 * as well as to calculate the time spent by a person on each project they are associated with.
 *
 * This class is annotated with @Component, making it a Spring-managed bean.
 * It is also annotated with @Transactional, indicating that methods in this class
 * should be executed within a transaction context.
 */
@Component
public class PersonService {

    private final PersonRepository personRepository;

    /**
     * Constructor for PersonService.
     *
     * @param personRepository   The repository for Person entities.
     */
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Retrieves all Person entities from the database.
     *
     * @return A list of PersonDetailDTO objects representing all persons.
     */
    @Transactional(readOnly = true)
    public List<PersonDetailDTO> getAllPeople() {
        return personRepository.findAll().stream().map(this::mapPersonToPersonDetailDTO).collect(Collectors.toList());
    }


    /**
     * Retrieves a Person entity by its ID.
     *
     * @param id The ID of the person to retrieve.
     * @return An Optional containing the PersonDetailDTO if found, or an empty Optional if not found.
     */
    @Transactional(readOnly = true)
    public Optional<PersonDetailDTO> getPersonById(int id) {
        return personRepository.findById(id)
                .map(this::mapPersonToPersonDetailDTO);
    }

    /**
     * Creates a new Person entity from the provided PersonDTO.
     * @param personDTO The DTO containing the details of the person to create.
     * @return A PersonDetailDTO representing the created person.
     */
    @Transactional
    public PersonDetailDTO createPerson(PersonDTO personDTO) {
        Person person = new Person(personDTO.getFirstName(), personDTO.getLastName());
        Person savedPerson =  personRepository.save(person);

        return mapPersonToPersonDetailDTO(savedPerson);
    }

    /**
     * Updates an existing Person entity by ID with the provided PersonDTO.
     *
     * @param id        The ID of the person to update.
     * @param personDTO The DTO containing the updated details of the person.
     * @return A PersonDetailDTO representing the updated person.
     */
    @Transactional
    public PersonDetailDTO updatePerson(int id, PersonDTO personDTO) {
        Person person = findPersonById(id);
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        Person updatedPerson = personRepository.save(person);

        return mapPersonToPersonDetailDTO(updatedPerson);
    }

    /**
     * Deletes a Person entity by ID.
     *
     * @param id The ID of the person to delete.
     */
    @Transactional
    public void deletePerson(int id) {
        Person person = findPersonById(id);
        for (Project project : new HashSet<>(person.getProjects())) {
            project.removePerson(person);
        }
            personRepository.delete(person);
    }

    /**
     * This is a helper method to find a Person entity by its ID.
     *
     * @param personId The ID of the person to retrieve.
     * @return The Person entity if found, or throws an IllegalArgumentException if not found.
     */
    private Person findPersonById(Integer personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + personId));
    }

    /**
     * Calculates the time spent by a person on each project they are associated with
     * via their timeslots.
     *
     * @param personId The ID of the person.
     * @return A list of ProjectTimeSummaryDTOs, each containing project info, total time, and percentage.
     */
    @Transactional(readOnly = true)
    public List<ProjectTimeSummaryDTO> getProjectTimeSummary(Integer personId) {
        Person person = findPersonById(personId);
        Set<TimeSlot> timeSlots = person.getTimeslots();
        Set<Project> allProjectsForSummary = new HashSet<>(person.getProjects());

        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.getProject() != null) {
                allProjectsForSummary.add(timeSlot.getProject());
            }
        }

        Map<Project, Long> secondsPerProject = calculateDurationPerProject(timeSlots);
        long totalSecondsOverall = secondsPerProject.values().stream().mapToLong(Long::longValue).sum();

        for (Project project : allProjectsForSummary) {
            secondsPerProject.putIfAbsent(project, 0L);
        }

        return createSummaryList(secondsPerProject, totalSecondsOverall);
    }

    /**
     * This helper method maps a Person entity to a PersonDetailDTO.
     *
     * @param person The Person entity to be mapped.
     * @return A PersonDetailDTO object containing the details of the person.
     */
    private PersonDetailDTO mapPersonToPersonDetailDTO(Person person) {
        List<TimeSlotDetailDTO> timeSlotDetailDTOs = person.getTimeslots().stream()
                .map(TimeSlotDetailDTO::new)
                .collect(Collectors.toList());
        List<ProjectBasicInfoDTO> projectBasicInfoDTOs = person.getProjects().stream()
                .map(project -> new ProjectBasicInfoDTO(project.getId(), project.getName()))
                .collect(Collectors.toList());
        return new PersonDetailDTO(person.getId(), person.getFirstName(), person.getLastName(),
                timeSlotDetailDTOs, projectBasicInfoDTOs);
    }

    /**
     * This method calculates the total duration of time slots for each project.
     *
     * @param timeSlots The set of time slots to calculate durations from.
     * @return A map where the key is the project and the value is the total duration in seconds.
     */
    private Map<Project, Long> calculateDurationPerProject(Set<TimeSlot> timeSlots) {
        Map<Project, Long> secondsPerProject = new HashMap<>();
        for (TimeSlot ts : timeSlots) {
            if (ts.getStartTime() != null && ts.getEndTime() != null && ts.getProject() != null) {
                long durationSeconds = Duration.between(ts.getStartTime(), ts.getEndTime()).toSeconds();
                if (durationSeconds > 0) {
                    secondsPerProject.merge(ts.getProject(), durationSeconds, Long::sum);
                }
            }
        }
        return secondsPerProject;
    }

    /**
     * This method creates a list of ProjectTimeSummaryDTOs from the calculated durations.
     *
     * @param secondsPerProject The map of projects and their corresponding durations.
     * @param totalSecondsOverall The total duration across all projects.
     * @return A list of ProjectTimeSummaryDTOs.
     */
    private List<ProjectTimeSummaryDTO> createSummaryList(Map<Project,
            Long> secondsPerProject, long totalSecondsOverall) {
        List<ProjectTimeSummaryDTO> summaryList = new ArrayList<>();
        for (Map.Entry<Project, Long> entry : secondsPerProject.entrySet()) {
            Project project = entry.getKey();
            long seconds = entry.getValue();
            double percentage = totalSecondsOverall > 0 ? (seconds * 100.0) / totalSecondsOverall : 0.0;
            summaryList.add(new ProjectTimeSummaryDTO(project.getId(), project.getName(), seconds, percentage));
        }
        return summaryList;
    }
}
