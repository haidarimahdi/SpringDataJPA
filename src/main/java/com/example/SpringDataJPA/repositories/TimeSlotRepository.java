package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.TimeSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    Page<TimeSlot> findByProjectId(Integer projectId, Pageable pageable);

    /**
     * Retrieves all TimeSlots for a specific project.
     * @param projectId the ID of the project
     * @return a list of TimeSlots associated with the specified project, grouped by person
     */
    @Query("SELECT ts.person.id as personId, SUM(ts.durationInSeconds) as totalDuration " +
           "FROM TimeSlot ts " +
           "WHERE ts.project.id = :projectId " +
           "GROUP BY ts.person.id")
    List<Map<String, Object>> sumDurationsPerPersonForProject(@Param("projectId") Integer projectId);
}