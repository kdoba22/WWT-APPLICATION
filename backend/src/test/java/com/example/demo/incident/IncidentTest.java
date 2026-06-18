package com.example.demo.incident;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class IncidentTest {

	@Test
	void storesAndReturnsIncidentFields() {
		Incident incident = new Incident();
		incident.setId(1L);
		incident.setIncidentName("Server outage");
		incident.setIncidentOwner("Jane Smith");
		incident.setIncidentOpenedBy("John Doe");
		incident.setIncidentSeverity("High");
		incident.setIncidentDate(LocalDate.of(2026, 6, 18));
		incident.setIncidentDetails("Primary database unavailable");
		incident.setIncidentNotes("Escalated to on-call engineer");

		assertThat(incident.getId()).isEqualTo(1L);
		assertThat(incident.getIncidentName()).isEqualTo("Server outage");
		assertThat(incident.getIncidentOwner()).isEqualTo("Jane Smith");
		assertThat(incident.getIncidentOpenedBy()).isEqualTo("John Doe");
		assertThat(incident.getIncidentSeverity()).isEqualTo("High");
		assertThat(incident.getIncidentDate()).isEqualTo(LocalDate.of(2026, 6, 18));
		assertThat(incident.getIncidentDetails()).isEqualTo("Primary database unavailable");
		assertThat(incident.getIncidentNotes()).isEqualTo("Escalated to on-call engineer");
	}

}
