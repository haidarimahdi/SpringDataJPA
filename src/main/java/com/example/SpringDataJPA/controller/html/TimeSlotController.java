package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.*;
import com.example.SpringDataJPA.service.PersonService;
import com.example.SpringDataJPA.service.ProjectService;
import com.example.SpringDataJPA.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("timeSlot")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;
    private final PersonService personService;
    private final ProjectService projectService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService, PersonService personService, ProjectService projectService) {
        this.timeSlotService = timeSlotService;
        this.personService = personService;
        this.projectService = projectService;
    }

    /**
     * Show a paginated list of all time slots.
     */
    @GetMapping("/")
    public String showAllTimeSlots(Model model, @PageableDefault(size = 10, sort = "date") Pageable pageable) {
        Page<TimeSlotDetailDTO> timeSlotPage = timeSlotService.getAllTimeSlotsPaginated(pageable);
        model.addAttribute("timeSlotPage", timeSlotPage);

        return "timeSlot-index";
    }

    /**
     * Show details for a specific TimeSlot.
     */
    @GetMapping("/{id}")
    public String showTimeSlotDetails(Model model, @PathVariable("id") Integer id) {
        Optional<TimeSlotDetailDTO> timeSlotDetailOpt = timeSlotService.getTimeSlotById(id);
        if (timeSlotDetailOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TimeSlot not found with id: " + id);
        }

        model.addAttribute("timeSlot", timeSlotDetailOpt.get());
        return "timeSlot-details";
    }

    /**
     * Show a paginated list of time slots for a specific project.
     */
    @GetMapping("/project/{projectId}")
    public String showProjectTimeSlots(@PathVariable Integer projectId, Model model,
                                       @PageableDefault(size = 10, sort = "date") Pageable pageable) {
        Page<TimeSlotDetailDTO> timeSlotPage = timeSlotService.getTimeSlotsByProjectId(projectId, pageable);
        model.addAttribute("timeSlotPage", timeSlotPage);
        model.addAttribute("projectId", projectId); // Pass projectId for pagination links
        return "project-timeSlots";
    }

    /**
     * Show form to create a new time slot.
     * Provides a list of persons and projects to select from.
     */
    @GetMapping("/new")
    public String showTimeSlotForm(@RequestParam(name = "personId", required = false) Integer personIdParam,
                                   @RequestParam(name = "projectId", required = false) Integer projectIdParam,
                                   Model model) {
        List<PersonDetailDTO> persons = personService.getAllPeople();
        List<ProjectBasicInfoDTO> projects = projectService.getAllProjectsBasicInfo();

        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();

        if (personIdParam != null) {
            timeSlotDTO.setPersonId(personIdParam);
        }

        if (projectIdParam != null) {
            timeSlotDTO.setProjectId(projectIdParam);
        }

        model.addAttribute("persons", persons);
        model.addAttribute("projects", projects);
        model.addAttribute("timeSlotDTO", timeSlotDTO);
        return "timeSlot-form";
    }

    /**
     * Create a new time slot from the provided TimeSlotDTO.
     * Redirects to the list of time slots after creation.
     * Throws an exception if the time slot data is invalid.
     */
    @PostMapping("/new")
    public String createTimeSlot(@ModelAttribute TimeSlotDTO timeSlotDTO) {
        try {
            timeSlotService.createTimeSlot(timeSlotDTO);
            return "redirect:/timeSlot/";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Delete a time slot by ID.
     * Redirects to the list of time slots after deletion.
     * Throws an exception if the time slot ID is invalid.
     */
    @PostMapping("/delete/{id}")
    public String deleteTimeSlot(@PathVariable Integer id) {
        try {
            timeSlotService.deleteTimeSlot(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        return "redirect:/timeSlot/";

    }
}
