package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.PersonDTO;
import com.example.SpringDataJPA.dto.ProjectTimeSummaryDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

/**
 * Service class for managing Person entities.
 * This class provides methods to create, update, delete, and retrieve Person entities,
 * as well as to calculate the time spent by a person on each project they are associated with.
 */
@Component
public class PersonService {

    private final PersonRepository personRepository;
    private final TimeSlotRepository timeSlotRepository;

    public PersonService(PersonRepository personRepository, TimeSlotRepository timeSlotRepository) {
        this.personRepository = personRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(int id) {
        return personRepository.findById(id);
    }

    public Person createPerson(PersonDTO personDTO) {
        Person person = new Person(personDTO.getFirstName(), personDTO.getLastName());
        return personRepository.save(person);
    }

    public Person updatePerson(int id, PersonDTO personDTO) {

        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            return null;
        }

        Person person = personOptional.get();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        return personRepository.save(person);
    }

    public void deletePerson(int id) {
        Optional<Person> personOptional = personRepository.findById(id);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            for (TimeSlot timeSlot : person.getTimeslots()) {
                timeSlotRepository.delete(timeSlot);
            }
            personRepository.delete(person);
        } else {
            throw new IllegalArgumentException("Invalid person Id:" + id);
        }
    }

    /**
     * Calculates the time spent by a person on each project they are associated with
     * via their timeslots.
     *
     * @param personId The ID of the person.
     * @return A list of ProjectTimeSummaryDTOs, each containing project info, total time, and percentage.
     */
    public List<ProjectTimeSummaryDTO> getProjectTimeSummary(Integer personId) {
        Person person = findPersonById(personId);
        Set<TimeSlot> timeSlots = person.getTimeslots();

        if (timeSlots == null || timeSlots.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Project, Long> secondsPerProject = calculateDurationPerProject(timeSlots);
        long totalSecondsOverall = secondsPerProject.values().stream().mapToLong(Long::longValue).sum();

        if (totalSecondsOverall == 0) {
            return Collections.emptyList();
        }

        return createSummaryList(secondsPerProject, totalSecondsOverall);
    }

    private Person findPersonById(Integer personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + personId));
    }

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

    private List<ProjectTimeSummaryDTO> createSummaryList(Map<Project, Long> secondsPerProject, long totalSecondsOverall) {
        List<ProjectTimeSummaryDTO> summaryList = new ArrayList<>();
        secondsPerProject.forEach((project, projectSeconds) -> {
            double percentage = (double) projectSeconds / totalSecondsOverall * 100.0;
            summaryList.add(new ProjectTimeSummaryDTO(
                    project.getId(),
                    project.getName(),
                    projectSeconds,
                    percentage
            ));
        });
        summaryList.sort(Comparator.comparingDouble(ProjectTimeSummaryDTO::percentage).reversed());
        return summaryList;
    }

}
