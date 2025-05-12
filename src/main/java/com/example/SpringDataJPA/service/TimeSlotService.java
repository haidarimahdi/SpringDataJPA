package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.TimeSlotDTO;
import com.example.SpringDataJPA.dto.TimeSlotDetailDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.ProjectRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing TimeSlot entities.
 * This class provides methods to create, update, delete, and retrieve TimeSlot entities,
 * as well as to manage the relationships between TimeSlots, Persons, and Projects.
 *
 * This class is annotated with @Component, making it a Spring-managed bean.
 * It is also annotated with @Transactional, indicating that methods in this class
 * should be executed within a transaction context.
 */
@Component
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TimeSlotService(TimeSlotRepository timeSlotRepository, PersonRepository personRepository,
                           ProjectRepository projectRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Get a paginated list of all TimeSlots as DTOs.
     * @param pageable Pagination information (page, size, sort)
     * @return A Page of TimeSlotDetailDTO entities.
     */
    @Transactional(readOnly = true)
    public Page<TimeSlotDetailDTO> getAllTimeSlotsPaginated(Pageable pageable) {
        Page<TimeSlot> timeSlotPage = timeSlotRepository.findAll(pageable);
        return timeSlotPage.map(TimeSlotDetailDTO::new);
    }

    @Transactional(readOnly = true)
    public List<TimeSlotDetailDTO> getAllTimeSlots() {
        return timeSlotRepository.findAll().stream().map(TimeSlotDetailDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Get a paginated list of TimeSlots for a specific project as DTOs.
     * @param projectId The ID of the project.
     * @param pageable Pagination information (page, size, sort)
     * @return A Page of TimeSlotDetailDTO entities for the given project.
     */
    @Transactional(readOnly = true)
    public Page<TimeSlotDetailDTO> getTimeSlotsByProjectId(Integer projectId, Pageable pageable) {
        Page<TimeSlot> timeSlotPage = timeSlotRepository.findByProjectId(projectId, pageable);
        return timeSlotPage.map(TimeSlotDetailDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<TimeSlotDetailDTO> getTimeSlotById(int id) {
        return timeSlotRepository.findById(id).map(TimeSlotDetailDTO::new);
    }

    /**
     * Creates a new TimeSlot associated with a Person and a Project.
     * Also ensures the Person is added to the Project's list of persons.
     * @param timeSlotDTO DTO containing TimeSlot data including personId and projectId.
     * @return The saved TimeSlot entity, converted to TimeSlotDetailDTO.
     * @throws IllegalArgumentException if the personId or projectId is invalid.
     */
    @Transactional
    public TimeSlotDetailDTO createTimeSlot(TimeSlotDTO timeSlotDTO) {
        Person person = personRepository.findById(timeSlotDTO.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + timeSlotDTO.getPersonId()));

        Project project = projectRepository.findById(timeSlotDTO.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + timeSlotDTO.getProjectId()));

        TimeSlot timeSlot = new TimeSlot(
                timeSlotDTO.getDate(),
                timeSlotDTO.getStartTime(),
                timeSlotDTO.getEndTime(),
                timeSlotDTO.getDescription(),
                person, project);

        if (!project.getPersons().contains(person)) {
            project.addPerson(person);
            projectRepository.save(project);
        }
        TimeSlot savedTimeSlot = timeSlotRepository.save(timeSlot);
        return new TimeSlotDetailDTO(savedTimeSlot);
    }

    /**
     * Deletes a TimeSlot by its ID.
     * @param id The ID of the TimeSlot to delete.
     * @throws IllegalArgumentException if the timeslotId is invalid.
     */
    @Transactional
    public void deleteTimeSlot(Integer id) {
        if (!timeSlotRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid TimeSlot ID for deletion: " + id);
        }
        timeSlotRepository.deleteById(id);
    }

}
