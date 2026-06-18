package com.example.demo.incident;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CreateIncidentRequestTest {

	@Test
	void exposesRequestFields() {
		CreateIncidentRequest request = new CreateIncidentRequest(
				"Server outage",
				"Jane Smith",
				"John Doe",
				"High",
				LocalDate.of(2026, 6, 18),
				"Primary database unavailable",
				"Escalated to on-call engineer");

		assertThat(request.incidentName()).isEqualTo("Server outage");
		assertThat(request.incidentOwner()).isEqualTo("Jane Smith");
		assertThat(request.incidentOpenedBy()).isEqualTo("John Doe");
		assertThat(request.incidentSeverity()).isEqualTo("High");
		assertThat(request.incidentDate()).isEqualTo(LocalDate.of(2026, 6, 18));
		assertThat(request.incidentDetails()).isEqualTo("Primary database unavailable");
		assertThat(request.incidentNotes()).isEqualTo("Escalated to on-call engineer");
	}

}
