package com.example.demo.person;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

	List<Person> findByIncidentId(Long incidentId);

}
