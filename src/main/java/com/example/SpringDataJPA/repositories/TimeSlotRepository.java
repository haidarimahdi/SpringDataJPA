package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.TimeSlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    Page<TimeSlot> findByProjectId(Integer projectId, Pageable pageable);
}