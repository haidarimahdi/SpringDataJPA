package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.TimeSlotDTO;
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
     * Get a paginated list of all TimeSlots.
     * @param pageable Pagination information (page, size, sort)
     * @return A Page of TimeSlot entities.
     */
    @Transactional(readOnly = true)
    public Page<TimeSlot> getAllTimeSlotsPaginated(Pageable pageable) {
        return timeSlotRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    /**
     * Get a paginated list of TimeSlots for a specific project.
     * @param projectId The ID of the project.
     * @param pageable Pagination information (page, size, sort)
     * @return A Page of TimeSlot entities for the given project.
     */
    @Transactional(readOnly = true)
    public Page<TimeSlot> getTimeSlotsByProjectId(Integer projectId, Pageable pageable) {
        return timeSlotRepository.findByProjectId(projectId, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<TimeSlot> getTimeSlotById(int id) {
        return timeSlotRepository.findById(id);
    }

    /**
     * Creates a new TimeSlot associated with a Person and a Project.
     * @param timeSlotDTO DTO containing TimeSlot data including personId and projectId.
     * @return The saved TimeSlot entity.
     * @throws IllegalArgumentException if the personId or projectId is invalid.
     */
    @Transactional
    public TimeSlot createTimeSlot(TimeSlotDTO timeSlotDTO) {
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

        return timeSlotRepository.save(timeSlot);
    }

    /**
     * Updates an existing TimeSlot's date, time, and description.
     * @param id The ID of the TimeSlot to update.
     * @param timeSlotDTO DTO containing the updated information.
     * @return The updated TimeSlot entity, or null if not found.
     */
    @Transactional
    public TimeSlot updateTimeSlot(Integer id, TimeSlotDTO timeSlotDTO) {
        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(id);
        if (timeSlotOptional.isEmpty()) {
             throw new IllegalArgumentException("Invalid TimeSlot ID for update: " + id);
        }

        TimeSlot timeSlot = timeSlotOptional.get();
        timeSlot.setDate(timeSlotDTO.getDate());
        timeSlot.setStartTime(timeSlotDTO.getStartTime());
        timeSlot.setEndTime(timeSlotDTO.getEndTime());
        timeSlot.setDescription(timeSlotDTO.getDescription());
        timeSlot.setPerson(personRepository.findById(timeSlotDTO.getPersonId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + timeSlotDTO.getPersonId())));

        return timeSlotRepository.save(timeSlot);
    }

    /**
     * Deletes a TimeSlot by its ID.
     * @param id The ID of the TimeSlot to delete.
     * @throws IllegalArgumentException if the timeslotId is invalid (optional change).
     */
    @Transactional
    public void deleteTimeSlot(Integer id) {
        if (!timeSlotRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid TimeSlot ID for deletion: " + id);
        }
        timeSlotRepository.deleteById(id);
    }

}
