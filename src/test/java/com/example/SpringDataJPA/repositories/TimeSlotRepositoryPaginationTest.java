package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test Class for TimeSlotRepository with pagination and sorting.
 * This test class uses Spring's Data JPA testing support to
 * test the repository methods.
 */
@DataJpaTest
public class TimeSlotRepositoryPaginationTest {
    @Autowired
    private TimeSlotRepository timeSlotRepository;

     @Autowired private ProjectRepository projectRepository;
     @Autowired private PersonRepository personRepository;

     @BeforeEach
     void setUp() {
         // Setup code to initialize the database with test data
         Person personA = new Person("John", "Doe");
         personRepository.save(personA);

         Project projectA = new Project("Project A", "Description A", 10);
         projectRepository.save(projectA);

         // Create and save 15 TimeSlot entities
         IntStream.range(0, 15).forEach(i -> {
             TimeSlot timeSlot = new TimeSlot(
                     LocalDate.now().minusDays(i),
                     LocalTime.of(9, 0),
                     LocalTime.of(17, 0),
                     "Description " + i,
                     personA,
                     projectA
             );
             timeSlotRepository.save(timeSlot);
         });
     }

    @Test
    void testFindAllPaginatedAndSorted() {
        // Act: Request page 1 (second page), size 10, sorted by date descending
        Pageable pageable = PageRequest.of(1, 10, Sort.by("date").descending());
        Page<TimeSlot> resultPage = timeSlotRepository.findAll(pageable);

        // Assert: Check page metadata and content
        assertThat(resultPage.getTotalElements()).isEqualTo(15); // 15 total saved
        assertThat(resultPage.getTotalPages()).isEqualTo(2);
        assertThat(resultPage.getNumber()).isEqualTo(1); // Current page is 1
        assertThat(resultPage.getContent()).hasSize(5); // 5 elements on the second page
        assertThat(resultPage.getContent()).isSortedAccordingTo((ts1, ts2) -> ts2.getDate().compareTo(ts1.getDate()));
    }

    @Test
    void testFindByProjectIdPaginated() {
        // Arrange: Get the project ID
        Project projectA = projectRepository.findAll().get(0);
        Integer projectIdToFind = projectA.getId();

        // Act: Request page 0, size 2 for the specific project ID
        Pageable pageable = PageRequest.of(0, 2);
        Page<TimeSlot> resultPage = timeSlotRepository.findByProjectId(projectIdToFind, pageable);

        // Assert: Check results specific to the project
        assertThat(resultPage.getTotalElements()).isEqualTo(15); // 15 slots for projectA
        assertThat(resultPage.getTotalPages()).isEqualTo(8);
        assertThat(resultPage.getNumber()).isEqualTo(0);
        assertThat(resultPage.getContent()).hasSize(2);
        assertThat(resultPage.getContent())
                .allSatisfy(ts -> assertThat(ts.getProject().getId()).isEqualTo(projectIdToFind));
    }

}
