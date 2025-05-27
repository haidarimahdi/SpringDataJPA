package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    /**
     * Fetches the project with its persons.
     * @param id the project ID
     * @return an Optional containing the project with its persons, or empty if not found
     */
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.persons WHERE p.id = :id")
    Optional<Project> findByIdWithPersons(@Param("id") Integer id);

    /**
     * Fetches all projects with their time slots.
     * This is useful for avoiding N+1 problems when accessing time slots.
     * @return a list of projects with their time slots
     */
    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.timeSlots ts LEFT JOIN FETCH p.persons")
    List<Project> findAllWithTimeSlots();

    /**
     * Fetches the project with its persons and their time slots.
     * This is useful for avoiding N+1 problems when accessing persons and their time slots.
     * @param id the project ID
     * @return an Optional containing the project with its persons and their time slots, or empty if not found
     */
    @Query("SELECT DISTINCT pr FROM Project pr " +
            "LEFT JOIN FETCH pr.persons p " +
            "LEFT JOIN FETCH p.timeSlots ts " +
            "WHERE pr.id = :id AND ts.project.id = :id")
    Optional<Project> findByIdWithPersonsAndFilteredTimeSlots(@Param("id") Integer id);


    /**
     * Retrieves a project by ID.
     * @param projectId the project ID
     * @return a list of projects
    */
    @Query("SELECT p FROM Project p WHERE p.id = :projectId")
    List<Project> findById(Long projectId);

    @Query("SELECT SUM(ts.durationInSeconds) FROM TimeSlot ts WHERE ts.project.id = :projectId AND " +
            "ts.person MEMBER OF ts.project.persons")
    Long sumDurationInSecondsForProject(@Param("projectId") Integer projectId);
}