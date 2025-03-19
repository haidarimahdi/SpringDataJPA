package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.PersonDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.repositories.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
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

    public Person updatePerson(Integer id, PersonDTO personDTO) {

        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            return null;
        }

        Person person = personOptional.get();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        return personRepository.save(person);
    }

    public void deletePerson(Integer id) {
        personRepository.deleteById(id);
    }
}
