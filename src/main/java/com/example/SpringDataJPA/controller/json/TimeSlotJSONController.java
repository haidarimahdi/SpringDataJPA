package com.example.SpringDataJPA.controller.json;

import com.example.SpringDataJPA.dto.TimeSlotDTO;
import com.example.SpringDataJPA.dto.TimeSlotDetailDTO;
import com.example.SpringDataJPA.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * REST controller for managing TimeSlot entities in JSON format.
 * This class provides endpoints for creating, updating, deleting,
 * and retrieving time slot details in JSON format.
 */
@RestController
@RequestMapping("/timeSlot")
public class TimeSlotJSONController {
    private final TimeSlotService timeSlotService;

    /**
     * Constructor for TimeSlotJSONController.
     *
     * @param timeSlotService the service to manage TimeSlot entities
     */
    @Autowired
    public TimeSlotJSONController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }


    /**
     * Get a paginated list of all time slots in JSON format.
     */
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Page<TimeSlotDetailDTO>> getAllTimeSlotsJSONPaginated(
            @PageableDefault(size = 20, sort = "date") Pageable pageable) {
        Page<TimeSlotDetailDTO> pageResult = timeSlotService.getAllTimeSlotsPaginated(pageable);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * Get the details of a time slot by ID in JSON format.
     *
     * @param id the ID of the time slot
     * @return ResponseEntity containing the TimeSlotDetailDTO if found, or 404 Not Found status
     */
    @GetMapping(value = "{id}.json", produces = "application/json")
    public ResponseEntity<TimeSlotDetailDTO> getTimeSlotDetailJSON(@PathVariable("id") Integer id) {
        Optional<TimeSlotDetailDTO> timeSlotOptional = timeSlotService.getTimeSlotById(id);
        return timeSlotOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get a paginated list of time slots for a specific project in JSON format.
     */
    @GetMapping(value = "/project/{projectId}.json", produces = "application/json")
    public ResponseEntity<Page<TimeSlotDetailDTO>> getProjectTimeSlotsJSONPaginated(
            @PathVariable Integer projectId,
            @PageableDefault(size = 20, sort = "date") Pageable pageable) {
        Page<TimeSlotDetailDTO> pageResult = timeSlotService.getTimeSlotsByProjectId(projectId, pageable);
        return ResponseEntity.ok(pageResult);
    }


    /**
     * Create a new time slot from the provided TimeSlotDTO.
     *
     * @param timeSlotDTO the DTO containing the details of the time slot to create
     * @return ResponseEntity containing the created TimeSlotDetailDTO and the location URI
     */
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<TimeSlotDetailDTO> createTimeSlotJSON(@RequestBody TimeSlotDTO timeSlotDTO) {
        try {
            TimeSlotDetailDTO updatedTimeSlot = timeSlotService.createTimeSlot(timeSlotDTO);
            return ResponseEntity.ok(updatedTimeSlot);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * Delete a time slot by ID.
     *
     * @param id the ID of the time slot to delete
     * @return ResponseEntity with no content if successful, or 404 Not Found status if the time slot does not exist
     */
    @DeleteMapping(value = "{id}.json")
    public ResponseEntity<Void> deleteTimeSlotJSON(@PathVariable("id") Integer id) {
        try {
            timeSlotService.deleteTimeSlot(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
