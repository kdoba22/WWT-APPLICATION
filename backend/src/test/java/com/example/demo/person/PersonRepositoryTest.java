package com.example.demo.person;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.incident.Incident;
import com.example.demo.incident.IncidentRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonRepositoryTest {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private PersonRepository personRepository;

	@Test
	void savesPersonLinkedToIncident() {
		Incident incident = new Incident();
		incident.setIncidentName("Server outage");
		incident.setIncidentOwner("Jane Smith");
		incident.setIncidentOpenedBy("John Doe");
		incident.setIncidentSeverity("High");
		incident.setIncidentDate(LocalDate.of(2026, 6, 18));
		incident.setIncidentDetails("Primary database unavailable");
		incident.setIncidentNotes("Escalated to on-call engineer");
		incident = incidentRepository.save(incident);

		Person person = new Person();
		person.setUserName("John Doe");
		person.setIncident(incident);
		person = personRepository.save(person);

		assertThat(person.getId()).isNotNull();
		assertThat(person.getUserName()).isEqualTo("John Doe");
		assertThat(person.getIncident().getId()).isEqualTo(incident.getId());

		assertThat(personRepository.findByIncidentId(incident.getId()))
				.hasSize(1)
				.first()
				.extracting(Person::getUserName)
				.isEqualTo("John Doe");
	}

}
