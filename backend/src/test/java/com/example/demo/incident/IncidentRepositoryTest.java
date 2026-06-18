package com.example.demo.incident;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IncidentRepositoryTest {

	@Autowired
	private IncidentRepository incidentRepository;

	@Test
	void savesAndFindsIncidentById() {
		Incident incident = new Incident();
		incident.setIncidentName("Repository test incident");
		incident.setIncidentOwner("Jane Smith");
		incident.setIncidentOpenedBy("John Doe");
		incident.setIncidentSeverity("Medium");
		incident.setIncidentDate(LocalDate.of(2026, 6, 18));
		incident.setIncidentDetails("Testing repository persistence");
		incident.setIncidentNotes("Created in test");

		Incident saved = incidentRepository.save(incident);

		assertThat(saved.getId()).isNotNull();
		assertThat(incidentRepository.findById(saved.getId()))
				.isPresent()
				.get()
				.extracting(Incident::getIncidentName)
				.isEqualTo("Repository test incident");
	}

}
