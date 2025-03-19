package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.TimeSlotDTO;
import com.example.SpringDataJPA.model.TimeSlot;
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

    TimeSlotRepository timeSlotRepository;
    TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotRepository timeSlotRepository, TimeSlotService timeSlotService) {
        this.timeSlotRepository = timeSlotRepository;
        this.timeSlotService = timeSlotService;
    }

    @GetMapping("/")
    public String showAllTimeSlots(Model model) {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        model.addAttribute("timeSlots", timeSlots);
        System.out.println("timeSlots.size() = " + timeSlots.size());
        model.addAttribute("timeSlotCount", timeSlots.size());
        return "timeSlot-index";
    }

    @GetMapping("/details/{id}")
    public String showTimeSlotDetails(Model model, @PathVariable("id") Integer id) {
        Optional<TimeSlot> timeSlotOptional = timeSlotService.getTimeSlotById(id);
        if (timeSlotOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid timeSlot Id:" + id);
        }
        TimeSlot timeSlot = timeSlotOptional.get();
        model.addAttribute("timeSlot", timeSlot);
        return "timeSlot-details";
    }

    @GetMapping("/new/{personId}")
    public String showTimeSlotForm(Model model, @PathVariable("personId") Integer personId) {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setPersonId(personId);
        model.addAttribute("timeSlotDTO", timeSlotDTO);
        model.addAttribute("personId", personId);
        return "timeSlot-form";
    }

    @PostMapping("/new")
    public String createTimeSlot(@ModelAttribute TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = timeSlotService.createTimeSlot(timeSlotDTO);
        if (timeSlot == null) {
            return "redirect:/person/details/" + timeSlotDTO.getPersonId();
        }
        return "redirect:/timeSlot/details/" + timeSlot.getId();
    }

    @GetMapping("/update/{id}")
    public String showUpdateTimeSlotForm(Model model, @PathVariable Integer id) {
        Optional<TimeSlot> timeSlotOptional = timeSlotService.getTimeSlotById(id);
        if (timeSlotOptional.isPresent()) {
            TimeSlot timeSlot = timeSlotOptional.get();
            TimeSlotDTO timeSlotDTO = new TimeSlotDTO(timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime(),
                    timeSlot.getDescription(), timeSlot.getPerson().getId());
            model.addAttribute("timeSlotDTO", timeSlotDTO);
            model.addAttribute("timeSlotId", id);
            return "timeSlot-form";
        } else {
            throw new IllegalArgumentException("Invalid timeSlot Id:" + id);
        }
    }

    @PostMapping("/update/{id}")
    public String updateTimeSlot(@PathVariable Integer id, @ModelAttribute TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = timeSlotService.updateTimeSlot(id, timeSlotDTO);
        if (timeSlot == null) {
            return "redirect:/timeSlot/update/" + id + "?error";
        }
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
