//package com.example.SpringDataJPA.repositories;
//
//import com.example.SpringDataJPA.model.Person;
//import com.example.SpringDataJPA.model.TimeSlot;
//import com.example.SpringDataJPA.repositories.PersonRepository;
//import com.example.SpringDataJPA.repositories.TimeSlotRepository;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class PersonRepositoryTest {
//
//    private PersonRepository personRepository;
//
//    private TimeSlotRepository timeslotRepository;
//
//    @Test
//    public void test_retrieveAllAndCalculateHoursThreePersonsZeroThreeOneTimeslot() {
//        // Create test data
//        // Create persons
//        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
//        ArrayList<Person> persons = new ArrayList<>(names.length);
//        for (String[] name : names) {
//            Person person = new Person(name[0], name[1]);
//            persons.add(person);
//        }
//
//        //Create timeslots
//        List<TimeSlot> timeSlots = new LinkedList<>();
//        TimeSlot timeslot = new TimeSlot(LocalDate.of(2023, 4, 30)
//                , LocalTime.of(9, 30)
//                , LocalTime.of(10, 45)
//                , "PS2"
//                , persons.get(1));
//        persons.get(1).addTimeslot(timeslot);
//        timeSlots.add(timeslot);
//
//        timeslot = new TimeSlot(LocalDate.of(2023, 4, 30)
//                , LocalTime.of(11, 0)
//                , LocalTime.of(12, 15)
//                , "PS2"
//                , persons.get(1));
//        persons.get(1).addTimeslot(timeslot);
//        timeSlots.add(timeslot);
//
//        timeslot = new TimeSlot(LocalDate.of(2023, 5, 2)
//                , LocalTime.of(11, 0)
//                , LocalTime.of(12, 15)
//                , "APF"
//                , persons.get(1));
//        persons.get(1).addTimeslot(timeslot);
//        timeSlots.add(timeslot);
//
//        timeslot = new TimeSlot(LocalDate.of(2023, 4, 30)
//                , LocalTime.of(9, 30)
//                , LocalTime.of(10, 45)
//                , "PS2"
//                , persons.get(2));
//        persons.get(2).addTimeslot(timeslot);
//        timeSlots.add(timeslot);
//
//        personRepository.saveAll(persons);
//        timeslotRepository.saveAll(timeSlots);
//
//        List<Person> resultRetrieveAll = personRepository.retrieveAllActive();
//        List<PersonRepository.PersonWithHours> resultCalculateHours = personRepository.calculateHours();
//
//        Assertions.assertEquals(2, resultRetrieveAll.size());
//
//        Assertions.assertEquals("Mimi", resultRetrieveAll.get(0).getFirstName());
//        Assertions.assertEquals("Meier", resultRetrieveAll.get(0).getLastName());
//        Assertions.assertEquals(3, resultRetrieveAll.get(0).getTimeslots().size());
//
//        Assertions.assertEquals("Hugo", resultRetrieveAll.get(1).getFirstName());
//        Assertions.assertEquals("Hugoson", resultRetrieveAll.get(1).getLastName());
//        Assertions.assertEquals(1, resultRetrieveAll.get(1).getTimeslots().size());
//
//        Assertions.assertEquals(3, resultCalculateHours.size());
//
//        Assertions.assertEquals("Max", resultCalculateHours.get(0).getFirstName());
//        Assertions.assertEquals("Meier", resultCalculateHours.get(0).getLastName());
//        Assertions.assertEquals(0, resultCalculateHours.get(0).getSlotCount());
//
//        Assertions.assertEquals("Mimi", resultCalculateHours.get(1).getFirstName());
//        Assertions.assertEquals("Meier", resultCalculateHours.get(1).getLastName());
//        Assertions.assertEquals(3, resultCalculateHours.get(1).getSlotCount());
//
//        Assertions.assertEquals("Hugo", resultCalculateHours.get(2).getFirstName());
//        Assertions.assertEquals("Hugoson", resultCalculateHours.get(2).getLastName());
//        Assertions.assertEquals(1, resultCalculateHours.get(2).getSlotCount());
//    }
//
//    @Test
//    public void test_retrieveAll_noTimeSlots() {
//        // Create persons
//        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
//        ArrayList<Person> persons = new ArrayList<>(names.length);
//        for (String[] name : names) {
//            Person person = new Person(name[0], name[1]);
//            persons.add(person);
//        }
//
//        personRepository.saveAll(persons);
//
//        List<Person> resultRetrieveAll = personRepository.retrieveAllActive();
//        Assertions.assertEquals(0, resultRetrieveAll.size());
//    }
//
//    @Test
//    public void test_retrieveAll_singleTimeSlot() {
//        // Create persons
//        Person person = new Person("Max", "Meier");
//
//        // Create timeslot
//        TimeSlot timeslot = new TimeSlot(LocalDate.of(2023, 4, 30)
//                , LocalTime.of(9, 30)
//                , LocalTime.of(10, 45)
//                , "PS2"
//                , person);
//        person.addTimeslot(timeslot);
//
//        personRepository.save(person);
//        timeslotRepository.save(timeslot);
//
//        List<Person> resultRetrieveAll = personRepository.retrieveAllActive();
//        Assertions.assertEquals(1, resultRetrieveAll.size());
//
//        Assertions.assertEquals("Max", resultRetrieveAll.get(0).getFirstName());
//        Assertions.assertEquals("Meier", resultRetrieveAll.get(0).getLastName());
//        Assertions.assertEquals(1, resultRetrieveAll.get(0).getTimeslots().size());
//    }
//
//    @Test
//    public void test_retrieveAll_multiplePersonsDifferentTimeSlots() {
//        // Create persons
//        String[][] names = {{"Max", "Mustermann"}, {"Erika", "Mustermann"}, {"Hans", "Peter"}};
//        ArrayList<Person> persons = new ArrayList<>(names.length);
//        for (String[] name : names) {
//            Person person = new Person(name[0], name[1]);
//            persons.add(person);
//        }
//
//        // Create timeslots
//        List<TimeSlot> timeSlots = new LinkedList<>();
//        TimeSlot timeslot1 = new TimeSlot(LocalDate.of(2023, 5, 10)
//                , LocalTime.of(10, 0)
//                , LocalTime.of(11, 0)
//                , "Meeting"
//                , persons.get(0));
//        persons.get(0).addTimeslot(timeslot1);
//        timeSlots.add(timeslot1);
//
//        TimeSlot timeslot2 = new TimeSlot(LocalDate.of(2023, 5, 11)
//                , LocalTime.of(14, 0)
//                , LocalTime.of(15, 0)
//                , "Workshop"
//                , persons.get(1));
//        persons.get(1).addTimeslot(timeslot2);
//        timeSlots.add(timeslot2);
//
//        TimeSlot timeslot3 = new TimeSlot(LocalDate.of(2023, 5, 12)
//                , LocalTime.of(11, 0)
//                , LocalTime.of(12, 0)
//                , "Consultation"
//                , persons.get(2));
//        persons.get(2).addTimeslot(timeslot3);
//        timeSlots.add(timeslot3);
//
//        personRepository.saveAll(persons);
//        timeslotRepository.saveAll(timeSlots);
//
//        List<Person> resultRetrieveAll = personRepository.retrieveAllActive();
//        Assertions.assertEquals(3, resultRetrieveAll.size());
//
//        Assertions.assertEquals("Max", resultRetrieveAll.get(0).getFirstName());
//        Assertions.assertEquals("Mustermann", resultRetrieveAll.get(0).getLastName());
//        Assertions.assertEquals(1, resultRetrieveAll.get(0).getTimeslots().size());
//
//        Assertions.assertEquals("Erika", resultRetrieveAll.get(1).getFirstName());
//        Assertions.assertEquals("Mustermann", resultRetrieveAll.get(1).getLastName());
//        Assertions.assertEquals(1, resultRetrieveAll.get(1).getTimeslots().size());
//
//        Assertions.assertEquals("Hans", resultRetrieveAll.get(2).getFirstName());
//        Assertions.assertEquals("Peter", resultRetrieveAll.get(2).getLastName());
//        Assertions.assertEquals(1, resultRetrieveAll.get(2).getTimeslots().size());
//    }
//
//    @Test
//    public void test_calculateHours_noTimeSlots() {
//        // Create persons
//        String[][] names = {{"Max", "Meier"}, {"Mimi", "Meier"}, {"Hugo", "Hugoson"}};
//        ArrayList<Person> persons = new ArrayList<>(names.length);
//        for (String[] name : names) {
//            Person person = new Person(name[0], name[1]);
//            persons.add(person);
//        }
//
//        personRepository.saveAll(persons);
//
//        List<PersonRepository.PersonWithHours> resultCalculateHours = personRepository.calculateHours();
//        Assertions.assertEquals(3, resultCalculateHours.size());
//
//        for (PersonRepository.PersonWithHours personWithHours : resultCalculateHours) {
//            Assertions.assertEquals(0, personWithHours.getSlotCount());
//        }
//    }
//}
