package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.PersonDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonService {

    private PersonRepository personRepository;

    private TimeSlotRepository timeSlotRepository;

    public PersonService(PersonRepository personRepository, TimeSlotRepository timeSlotRepository) {
        this.personRepository = personRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<Person> getAllPeople() {
        return personRepository.retrieveAllActive();
    }

    public Optional<Person> getPersonById(int id) {
        return personRepository.findById(id);
    }

    public Person createPerson(PersonDTO personDTO) {
        Person person = new Person(personDTO.getFirstName(), personDTO.getLastName());
        return personRepository.save(person);
    }

    public Person updatePerson(int id, PersonDTO personDTO) {

        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            return null;
        }

        Person person = personOptional.get();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        return personRepository.save(person);
    }

    public void deletePerson(int id) {
        Optional<Person> personOptional = personRepository.findById(id);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            for (TimeSlot timeSlot : person.getTimeslots()) {
                timeSlotRepository.delete(timeSlot);
            }
            personRepository.delete(person);
        } else {
            throw new IllegalArgumentException("Invalid person Id:" + id);
        }
    }
}
