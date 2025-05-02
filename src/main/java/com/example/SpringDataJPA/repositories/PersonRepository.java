package com.example.SpringDataJPA.repositories;

import com.example.SpringDataJPA.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    /**
     * Retrieves all persons with at least one timeSlot. Together with their timeSlots to avoid n + 1 problem.
     * @return all person with timeSlots
     */
    @Query("""
            SELECT DISTINCT p FROM Person p
                        JOIN FETCH p.timeSlots ts 
                                    WHERE ts IS NOT NULL
            """)
    List<Person> retrieveAllActive();

    /**
     * Uses a native query to count the timeSlots for every person and combines the result with the first and last
     * name of the person.
     * @return all persons with first and last name and the count of timeSlots
     */
    @Query(value = """
            SELECT p.first_Name as firstName, p.Last_Name as lastName, COUNT(ts.id) as slotCount
                        FROM person p LEFT JOIN time_slot ts ON p.p_id = ts.person_p_id
                        GROUP BY p.p_id
            """, nativeQuery = true)
    List<PersonWithHours> calculateHours();


    /**
     * Retrieves a person by id
     * @param personId the id of the person
     * @return the person with all timeSlots
     */
    @Query("""
            SELECT p FROM Person p
                        WHERE p.id = :personId
            """)
    List<Person> findById(Long personId);


    /**
     * Interface for combining the count of timeSlots a person has booked together with the first and last name of
     * the person.
     */
    interface PersonWithHours{
        String getFirstName();
        String getLastName();
        Integer getSlotCount();
    }
}
