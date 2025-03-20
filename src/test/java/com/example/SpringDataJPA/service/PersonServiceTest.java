package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.PersonDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testCreatePerson() {
        PersonDTO personDTO = new PersonDTO("John", "Doe");
        Person person = new Person("John", "Doe");
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person createdPerson = personService.createPerson(personDTO);

        assertNotNull(createdPerson);
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void testGetAllPeople() {
        Person person = new Person("John", "Doe");
        when(personRepository.retrieveAllActive()).thenReturn(Collections.singletonList(person));

        assertFalse(personService.getAllPeople().isEmpty());
        verify(personRepository, times(1)).retrieveAllActive();
    }

    @Test
    public void testGetPersonById() {
//        @TODO
    }

    @Test
    public void testUpdatePerson() {
        PersonDTO personDTO = new PersonDTO("Jane", "Doe");
        Person person = new Person("John", "Doe");
        when(personRepository.findById(1)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person updatedPerson = personService.updatePerson(1, personDTO);

        assertNotNull(updatedPerson);
        assertEquals("Jane", updatedPerson.getFirstName());
        assertEquals("Doe", updatedPerson.getLastName());
        verify(personRepository, times(1)).findById(1);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    public void testDeletePerson() {
        PersonDTO personDTO = new PersonDTO("John", "Doe");
        Person person = new Person("John", "Doe");
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person createdPerson = personService.createPerson(personDTO);

        TimeSlot timeSlot = new TimeSlot(LocalDate.of(2023, 4, 30)
                , LocalTime.of(9, 30)
                , LocalTime.of(10, 45)
                , "PS2"
                , createdPerson);
        createdPerson.addTimeslot(timeSlot);
        when(personRepository.findById(1)).thenReturn(Optional.of(createdPerson));

        personService.deletePerson(createdPerson.getId());
        System.out.println(createdPerson.getTimeslots().size());

        System.out.println(createdPerson.getFirstName());
        verify(timeSlotRepository, times(1)).delete(timeSlot);
        verify(personRepository, times(1)).delete(person);
    }

}
