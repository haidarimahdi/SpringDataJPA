package com.example.SpringDataJPA.controller.json;

import com.example.SpringDataJPA.dto.PersonDTO;
import com.example.SpringDataJPA.dto.PersonDetailDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * REST controller for managing Person entities in JSON format.
 */
@RestController
@RequestMapping("/person")
public class PersonJSONController {
    private final PersonService personService;

    /**
     * Constructor for PersonJSONController.
     *
     * @param personService the service to manage Person entities
     */
    @Autowired
    public PersonJSONController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Get the details of a person by ID in JSON format.
     *
     * @param id the ID of the person
     * @return ResponseEntity containing the PersonDetailDTO if found, or 404 Not Found status
     */
    @GetMapping(value = "{id}.json", produces =  "application/json")
    public ResponseEntity<PersonDetailDTO> getPersonDetailJSON(@PathVariable("id") Integer id) {
        Optional<Person> personOptional = personService.getPersonById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            PersonDetailDTO dto = new PersonDetailDTO(person);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new person from the provided PersonDTO.
     *
     * @param personDTO the DTO containing the details of the person to create
     * @return ResponseEntity containing the created PersonDetailDTO and the location URI
     */
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<PersonDetailDTO> createPersonJSON(@RequestBody PersonDTO personDTO) {
        Person person = personService.createPerson(personDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}.json")
                .buildAndExpand(person.getId()).toUri();

        return ResponseEntity.created(location).body(new PersonDetailDTO(person));
    }

    /**
     * Update an existing person by ID with the provided PersonDTO.
     *
     * @param id the ID of the person to update
     * @param personDTO the DTO containing the updated details of the person
     * @return ResponseEntity containing the updated PersonDetailDTO if found, or 404 Not Found status
     */
    @PostMapping(value = "{id}.json", produces = "application/json")
    public ResponseEntity<PersonDetailDTO> updatePersonJSON(@PathVariable("id") Integer id,
                                                            @RequestBody PersonDTO personDTO) {
        Person person = personService.updatePerson(id, personDTO);
        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PersonDetailDTO(person));
    }

    /**
     * Delete a person by ID.
     *
     * @param id the ID of the person to delete
     * @return ResponseEntity with no content if successful, or 404 Not Found status if the person does not exist
     */
    @DeleteMapping(value = "{id}.json")
    public ResponseEntity<Void> deletePersonJSON(@PathVariable("id") Integer id) {
        try {
            personService.deletePerson(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
