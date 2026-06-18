package com.example.demo.person;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.incident.Incident;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	void storesAndReturnsPersonFields() {
		Incident incident = new Incident();
		incident.setId(1L);
		incident.setIncidentName("Server outage");
		incident.setIncidentOwner("Jane Smith");
		incident.setIncidentOpenedBy("John Doe");
		incident.setIncidentSeverity("High");
		incident.setIncidentDate(LocalDate.of(2026, 6, 18));
		incident.setIncidentDetails("Primary database unavailable");
		incident.setIncidentNotes("Escalated to on-call engineer");

		Person person = new Person();
		person.setId(5L);
		person.setUserName("John Doe");
		person.setIncident(incident);

		assertThat(person.getId()).isEqualTo(5L);
		assertThat(person.getUserName()).isEqualTo("John Doe");
		assertThat(person.getIncident().getId()).isEqualTo(1L);
	}

}
