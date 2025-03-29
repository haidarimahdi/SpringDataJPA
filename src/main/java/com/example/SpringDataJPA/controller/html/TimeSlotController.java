package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.TimeSlotDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import com.example.SpringDataJPA.service.TimeSlotService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("timeSlot")
public class TimeSlotController {

    private final PersonRepository personRepository;
    TimeSlotRepository timeSlotRepository;
    TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotRepository timeSlotRepository, TimeSlotService timeSlotService, PersonRepository personRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotService = timeSlotService;
        this.personRepository = personRepository;
    }

    @GetMapping("/")
    public String showAllTimeSlots(Model model) {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        model.addAttribute("timeSlots", timeSlots);
        model.addAttribute("timeSlotCount", timeSlots.size());
        return "timeSlot-index";
    }

    @GetMapping("/{id}")
    public String showTimeSlotDetails(Model model, @PathVariable("id") Integer id) {
        Optional<TimeSlot> timeSlotOptional = timeSlotService.getTimeSlotById(id);
        if (timeSlotOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid timeSlot Id:" + id);
        }
        TimeSlot timeSlot = timeSlotOptional.get();
        model.addAttribute("timeSlot", timeSlot);
        return "timeSlot-details";
    }


    @GetMapping("/new")
    public String showTimeSlotForm(Model model) {
        List<Person> persons = personRepository.findAll();
        model.addAttribute("persons", persons);
        model.addAttribute("timeSlotDTO", new TimeSlotDTO());
        return "timeSlot-form";
    }

    @PostMapping("/new")
    public String createTimeSlot(@ModelAttribute TimeSlotDTO timeSlotDTO) {
        timeSlotService.createTimeSlot(timeSlotDTO);
        return "redirect:/timeSlot/";
    }

    @GetMapping("/update/{id}")
    public String showTimeSlotUpdate(Model model, @PathVariable Integer id) {
        Optional<TimeSlot> timeSlotOptional = timeSlotService.getTimeSlotById(id);
        if (timeSlotOptional.isPresent()) {
            TimeSlot timeSlot = timeSlotOptional.get();
            TimeSlotDTO timeSlotDTO = new TimeSlotDTO(timeSlot);
            model.addAttribute("timeSlotDTO", timeSlotDTO);
            model.addAttribute("persons", personRepository.findAll());
            model.addAttribute("timeSlotId", id);
            return "timeSlot-update";
        } else {
            throw new IllegalArgumentException("Invalid timeSlot Id:" + id);
        }
    }

    @PostMapping("/update/{id}")
    public String updateTimeSlot(@PathVariable Integer id, @ModelAttribute TimeSlotDTO timeSlotDTO) {
        timeSlotService.updateTimeSlot(id, timeSlotDTO);
        return "redirect:/timeSlot/details/" + id;
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
}
