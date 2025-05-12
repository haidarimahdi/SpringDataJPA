package com.example.SpringDataJPA.controller.json;

import com.example.SpringDataJPA.dto.PersonDTO;
import com.example.SpringDataJPA.dto.PersonDetailDTO;
import com.example.SpringDataJPA.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Person entities in JSON format.
 * This class provides endpoints for creating, updating, deleting,
 * and retrieving person details in JSON format.
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
     * Get a list of all persons (basic info).
     *
     * @return ResponseEntity containing a list of PersonDetailDTO
     */
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<PersonDetailDTO>> getAllPersonsJSON() {
        List<PersonDetailDTO> people = personService.getAllPeople();
        return ResponseEntity.ok(people);
    }

    /**
     * Get the details of a person by ID in JSON format.
     *
     * @param id the ID of the person
     * @return ResponseEntity containing the PersonDetailDTO if found, or 404 Not Found status
     */
    @GetMapping(value = "{id}.json", produces =  "application/json")
    public ResponseEntity<PersonDetailDTO> getPersonDetailJSON(@PathVariable("id") Integer id) {
        Optional<PersonDetailDTO> personOptional = personService.getPersonById(id);
        return personOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Create a new person from the provided PersonDTO.
     *
     * @param personDTO the DTO containing the details of the person to create
     * @return ResponseEntity containing the created PersonDetailDTO and the location URI
     */
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<PersonDetailDTO> createPersonJSON(@RequestBody PersonDTO personDTO) {
        try {
            PersonDetailDTO createdPerson = personService.createPerson(personDTO);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}.json")
                    .buildAndExpand(createdPerson.getId())
                    .toUri();

            return ResponseEntity.created(location).body(createdPerson);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating person: " + e.getMessage());
        }
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
        try {
            PersonDetailDTO updatedPerson = personService.updatePerson(id, personDTO);
            return ResponseEntity.ok(updatedPerson);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating person: " + e.getMessage(), e);
        }
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
