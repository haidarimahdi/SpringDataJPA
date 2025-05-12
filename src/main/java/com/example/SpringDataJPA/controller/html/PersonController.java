package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.PersonDTO;
import com.example.SpringDataJPA.dto.PersonDetailDTO;
import com.example.SpringDataJPA.dto.ProjectTimeSummaryDTO;
import com.example.SpringDataJPA.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Show the list of all persons using PersonDetailDTO.
     */
    @GetMapping("/")
    public String index(Model model) {
        List<PersonDetailDTO> peopleDetails = personService.getAllPeople();
        model.addAttribute("people", peopleDetails);
        return "person-index";
    }

    /**
     * Show details for a specific person, including their timeslots and project summary, using PersonDetailDTO.
     */
    @GetMapping("/{id}")
    public String personDetails(Model model, @PathVariable int id) {
        Optional<PersonDetailDTO> personDetailOpt = personService.getPersonById(id);
        if (personDetailOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found with id: " + id);
        }
        PersonDetailDTO personDetail = personDetailOpt.get();
        model.addAttribute("person", personDetail);

        List<ProjectTimeSummaryDTO> projectSummary = personService.getProjectTimeSummary(id);
        model.addAttribute("projectSummary", projectSummary);

        model.addAttribute("slots", personDetail.getTimeSlots());

        return "person-details";
    }

    /**
     * Show form for creating a new person.
     * This method initializes a new PersonDTO object and adds it to the model.
     * The form is displayed for the user to fill in the details.
     */
    @GetMapping("/new")
    public String showPersonForm(Model model) {
        model.addAttribute("personDTO", new PersonDTO());
        return "person-form";
    }


    @PostMapping("/new")
    public String createPerson(@ModelAttribute PersonDTO personDTO) {
        personService.createPerson(personDTO);
        return "redirect:/person/";
    }

    @GetMapping("/update/{id}")
    public String showPersonUpdate(Model model, @PathVariable Integer id) {
        Optional<PersonDetailDTO> person = personService.getPersonById(id);
        if (!person.isEmpty()) {
            PersonDetailDTO personDetail = person.get();
            PersonDTO personDTO = new PersonDTO(personDetail.getFirstName(), personDetail.getLastName());

            model.addAttribute("personDTO", personDTO);
            model.addAttribute("personId", id);
            return "person-update";
        } else {
            return "redirect:/person/";
        }
    }

    @PostMapping("/update/{id}")
    public String updatePerson(@PathVariable Integer id, @ModelAttribute PersonDTO personDTO) {
        personService.updatePerson(id, personDTO);
        return "redirect:/person/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deletePerson(@PathVariable Integer id) {
        personService.deletePerson(id);
        return "redirect:/person/";
    }
}
