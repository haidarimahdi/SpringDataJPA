package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.Person;
import com.example.SpringDataJPA.model.Project;
import com.example.SpringDataJPA.model.TimeSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    private Project project1, project2;
    private Person person1, person2, person3;

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        entityManager.clear();

        // Create and persist persons
        person1 = new Person("Alice", "Johnson");
        person2 = new Person("Bob", "Smith");
        person3 = new Person("Charlie", "Brown");

        personRepository.saveAll(List.of(person1, person2, person3));

        // Create projects and associate persons
        project1 = new Project("Project A", "Description A", 50);
        project1.addPerson(person1);
        project1.addPerson(person2);

        project2 = new Project("Project B", "Description B", 100);
        project2.addPerson(person2);
        project2.addPerson(person3);

        // Save projects
        projectRepository.saveAll(List.of(project1, project2));
    }

    @Test
    void testSumDurationInSecondsForProject_withRelevantTimeSlots() {
        // 1. Setup Data
        TimeSlot ts1 = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), "Work on Project A", person1, project1);
        TimeSlot ts2 = new TimeSlot(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), "Work on Project A", person2, project1);
        TimeSlot ts3 = new TimeSlot(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0), "Work on Project B", person2, project2);

        // Save time slots and flush to ensure they are persisted
        timeSlotRepository.saveAllAndFlush(List.of(ts1, ts2, ts3));

        // 2. Execute the query
        Long totalSeconds = projectRepository.sumDurationInSecondsForProject(project1.getId());
        Long totalSecondsForProject2 = projectRepository.sumDurationInSecondsForProject(project2.getId());

        // 3. Assert
        // The total duration for project1 should be the sum of ts1 and ts2 durations (3600s each)
        assertThat(totalSeconds)
                .as("Total seconds should be 7200L (3600 + 3600) for Project A, but was %s", totalSeconds)
                .isEqualTo(7200L);

        assertThat(totalSecondsForProject2)
                .as("Total seconds should be 3600L (only from person2's time slot) for Project B, but was %s", totalSecondsForProject2)
                .isEqualTo(3600L);

    }

    @Test
    void testSumDurationInSecondsForProject_withTimeSlotFromUnassignedPerson() {
        // 1. Setup Data
        Person assignedPersonEntity = new Person("Assigned", "User");
        Person unassignedPersonEntity = new Person("Unassigned", "User");

        // Save persons individually to ensure they are managed and have IDs
        assignedPersonEntity = personRepository.save(assignedPersonEntity);
        unassignedPersonEntity = personRepository.save(unassignedPersonEntity);

        Project projectEntity = new Project("Project Unassigned", "Test Desc", 50);
        // Add the managed 'assignedPersonEntity' to the project's collection
        projectEntity.addPerson(assignedPersonEntity);

        // Save the project and flush to ensure the person_project join table is updated
        Project savedProject = projectRepository.saveAndFlush(projectEntity);

        TimeSlot tsAssigned = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), "Work assigned", assignedPersonEntity, savedProject);

        TimeSlot tsUnassigned = new TimeSlot(LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(12, 0), "Work unassigned", unassignedPersonEntity, savedProject);

        // Save time slots and flush
        timeSlotRepository.saveAllAndFlush(List.of(tsAssigned, tsUnassigned));

        // 2. Execute the query
        Long totalSeconds = projectRepository.sumDurationInSecondsForProject(savedProject.getId());

        // 3. Assert
        // If the query correctly finds tsAssigned and sums its duration, totalSeconds should be 3600L.
        // If it still returns null, it means tsAssigned is NOT matching the MEMBER OF condition.
        assertThat(totalSeconds)
                .as("Total seconds should be 3600L (only from assigned person), but was %s", totalSeconds)
                .isEqualTo(3600L);
    }

    @Test
    void testSumDurationInSecondsForProject_withNoTimeSlots() {
        // 1. Setup Data
        Project project = new Project("Project Empty", "Test Desc", 20);
        Person p1 = new Person("Lonely", "Worker");
        project.addPerson(p1);

        personRepository.save(p1);
        Project savedProject = projectRepository.save(project);

        // (No TimeSlots created for this project)

        // 2. Execute
        Long totalSeconds = projectRepository.sumDurationInSecondsForProject(savedProject.getId());

        // 3. Assert
        // SUM of no rows in SQL typically results in NULL. Check how your DB/JPA provider handles this.
        // If it's null, the Long object will be null. If it's 0, then 0L.
        assertThat(totalSeconds).isNull(); // Or .isEqualTo(0L) if SUM returns 0 for no rows
    }

    @Test
    void getProjectDetails_shouldCorrectlySumAndAssignHoursFromRepository() {
        // 1. Setup Data
        Project project = new Project("Project Details", "Test Desc", 30);
        Person p1 = new Person("Detail", "Person");
        project.addPerson(p1);

        personRepository.save(p1);
        Project savedProject = projectRepository.save(project);

        TimeSlot ts1 = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), "Detail work 1", p1, savedProject);
        TimeSlot ts2 = new TimeSlot(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), "Detail work 2", p1, savedProject);

        timeSlotRepository.saveAllAndFlush(List.of(ts1, ts2));

        // 2. Execute the query
        Long totalSeconds = projectRepository.sumDurationInSecondsForProject(savedProject.getId());

        // 3. Assert
        assertThat(totalSeconds)
                .as("Total seconds should be 7200L (3600 + 3600) for Project Details, but was %s", totalSeconds)
                .isEqualTo(7200L);
    }

    @Test
    void sumDurationsPerPersonForProject_validProjectId_returnsCorrectDurations() {
        Project project = new Project("Project A", "Description A", 100);
        projectRepository.save(project);

        Person person1 = new Person("John", "Doe");
        Person person2 = new Person("Jane", "Smith");
        personRepository.saveAll(List.of(person1, person2));

        TimeSlot timeSlot1 = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), "Task 1", person1, project);
        TimeSlot timeSlot2 = new TimeSlot(LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(11, 0), "Task 2", person1, project);
        TimeSlot timeSlot3 = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0), "Task 3", person2, project);
        timeSlotRepository.saveAll(List.of(timeSlot1, timeSlot2, timeSlot3));

        List<Map<String, Object>> result = timeSlotRepository.sumDurationsPerPersonForProject(project.getId());

        assertThat(result).hasSize(2);
        assertThat(result).anySatisfy(map -> {
            assertThat(map.get("personId")).isEqualTo(person1.getId());
            assertThat(map.get("totalDuration")).isEqualTo(7200L); // 2 hours in seconds
        });
        assertThat(result).anySatisfy(map -> {
            assertThat(map.get("personId")).isEqualTo(person2.getId());
            assertThat(map.get("totalDuration")).isEqualTo(3600L); // 1 hour in seconds
        });
    }

    @Test
    void sumDurationsPerPersonForProject_noTimeSlots_returnsEmptyList() {
        Project project = new Project("Project B", "Description B", 50);
        projectRepository.save(project);

        List<Map<String, Object>> result = timeSlotRepository.sumDurationsPerPersonForProject(project.getId());

        assertThat(result).isEmpty();
    }

    @Test
    void sumDurationsPerPersonForProject_invalidProjectId_returnsEmptyList() {
        List<Map<String, Object>> result = timeSlotRepository.sumDurationsPerPersonForProject(-1);

        assertThat(result).isEmpty();
    }

}
