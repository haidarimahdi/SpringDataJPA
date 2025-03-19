package com.example.SpringDataJPA.service;

import com.example.SpringDataJPA.dto.TimeSlotDTO;
import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.repositories.PersonRepository;
import com.example.SpringDataJPA.repositories.TimeSlotRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TimeSlotService {

    private TimeSlotRepository timeSlotRepository;
    private PersonRepository personRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository, PersonRepository personRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.personRepository = personRepository;
    }

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    public Optional<TimeSlot> getTimeSlotById(Integer id) {
        return timeSlotRepository.findById(id);
    }

    public TimeSlot createTimeSlot(TimeSlotDTO timeSlotDTO) {
        Optional<Person> personOptional = personRepository.findById(timeSlotDTO.getPersonId());
        if (personOptional.isEmpty()) {
            return null;
        }
        Person person = personOptional.get();
        TimeSlot timeSlot = new TimeSlot(timeSlotDTO.getDate(), timeSlotDTO.getStartTime(), timeSlotDTO.getEndTime(),
                timeSlotDTO.getDescription(), person);
        return timeSlotRepository.save(timeSlot);
    }

    public TimeSlot updateTimeSlot(Integer id, TimeSlotDTO timeSlotDTO) {
        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(id);
        if (timeSlotOptional.isEmpty()) {
            return null;
        }
        TimeSlot timeSlot = timeSlotOptional.get();
        timeSlot.setDate(timeSlotDTO.getDate());
        timeSlot.setStartTime(timeSlotDTO.getStartTime());
        timeSlot.setEndTime(timeSlotDTO.getEndTime());
        timeSlot.setDescription(timeSlotDTO.getDescription());
        return timeSlotRepository.save(timeSlot);
    }

    public void deleteTimeSlot(Integer id) {
        timeSlotRepository.deleteById(id);
    }

}
