package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.TimeSlotDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.ProjectRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
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

//    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotService timeSlotService;
    private final PersonRepository personRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TimeSlotController(TimeSlotRepository timeSlotRepository, TimeSlotService timeSlotService,
                              PersonRepository personRepository, ProjectRepository projectRepository) {
//        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotService = timeSlotService;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Show paginated list of all time slots.
     * Accepts page, size, sort request parameters automatically via Pageable.
     */
    @GetMapping("/")
    public String showAllTimeSlots(Model model, @PageableDefault(size = 10, sort = "date") Pageable pageable) {
        Page<TimeSlot> timeSlotPage = timeSlotService.getAllTimeSlotsPaginated(pageable);
        model.addAttribute("timeSlotPage", timeSlotPage);
        return "timeSlot-index";
    }

    /**
     * Show paginated list of time slots for a specific project.
     */
    @GetMapping("/{id}")
    public String showTimeSlotDetails(Model model, @PathVariable("id") Integer id) {
        Optional<TimeSlot> timeSlotOptional = timeSlotService.getTimeSlotById(id);
        if (timeSlotOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid TimeSlot ID: " + id);
        }
        TimeSlot timeSlot = timeSlotOptional.get();

        model.addAttribute("timeSlot", timeSlot);
        return "timeSlot-details";
    }

    /**
     * Show paginated list of time slots for a specific project.
     */
    @GetMapping("/project/{projectId}")
    public String showProjectTimeSlots(@PathVariable Integer projectId, Model model,
                                       @PageableDefault(size = 10, sort = "date") Pageable pageable) {
        Page<TimeSlot> timeSlotPage = timeSlotService.getTimeSlotsByProjectId(projectId, pageable);
        model.addAttribute("timeSlotPage", timeSlotPage);
        model.addAttribute("projectId", projectId); // Pass projectId for pagination links
        return "project-timeSlots";
    }

    /**
     * Show form to create a new time slot.
     * Provides a list of persons and projects to select from.
     */
    @GetMapping("/new")
    public String showTimeSlotForm(Model model) {
        List<Person> persons = personRepository.findAll();
        List<Project> projects = projectRepository.findAll();

        model.addAttribute("persons", persons);
        model.addAttribute("projects", projects);
        model.addAttribute("timeSlotDTO", new TimeSlotDTO());
        return "timeSlot-form";
    }

    /**
     * Create a new time slot from the provided TimeSlotDTO.
     * Redirects to the list of time slots after creation.
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

    @PostMapping("/delete/{id}")
    public String deleteTimeSlot(@PathVariable Integer id) {
        Optional<TimeSlot> timeSlotOptional = timeSlotService.getTimeSlotById(id);
        if (timeSlotOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid timeSlot Id:" + id);
        }
        timeSlotService.deleteTimeSlot(id);
        return "redirect:/timeSlot/";
    }

    //================== Not asked in the assignment task ==================
//    /**
//     * Show the form to update an existing time slot.
//     * Provides a list of persons and projects to select from.
//     */
//    @GetMapping("/update/{id}")
//    public String showTimeSlotUpdate(Model model, @PathVariable Integer id) {
//        Optional<TimeSlot> timeSlotOptional = timeSlotService.getTimeSlotById(id);
//        if (timeSlotOptional.isPresent()) {
//            TimeSlot timeSlot = timeSlotOptional.get();
//            TimeSlotDTO timeSlotDTO = new TimeSlotDTO(timeSlot);
//
//            model.addAttribute("timeSlotDTO", timeSlotDTO);
//            model.addAttribute("persons", personRepository.findAll());
//            model.addAttribute("timeSlotId", id);
//
//            return "timeSlot-update";
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid TimeSlot ID for update: " + id);
//        }
//    }
//
//    /**
//     * Update an existing time slot with the provided TimeSlotDTO.
//     * Redirects to the details page of the updated time slot.
//     */
//    @PostMapping("/update/{id}")
//    public String updateTimeSlot(@PathVariable Integer id, @ModelAttribute TimeSlotDTO timeSlotDTO) {
//        timeSlotService.updateTimeSlot(id, timeSlotDTO);
//        return "redirect:/timeSlot/" + id;
//    }
//==========================================================================

}
