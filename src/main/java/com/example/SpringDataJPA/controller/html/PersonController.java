package com.example.SpringDataJPA.controller.html;

import com.example.SpringDataJPA.dto.PersonDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonService personService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("people", personRepository.findAll());
        return "person-index";
    }

    @GetMapping("/{id}")
    public String index(Model model, @PathVariable int id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            return "Error: No Person Found with id: " + id;
        }
        Person p = personRepository.findById(id).get();
        model.addAttribute("person", p);
        model.addAttribute("slots", p.getTimeslots());
        return "person-details";
    }

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
        Optional<Person> personOptional = personService.getPersonById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            PersonDTO personDTO = new PersonDTO(person.getFirstName(), person.getLastName());
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
