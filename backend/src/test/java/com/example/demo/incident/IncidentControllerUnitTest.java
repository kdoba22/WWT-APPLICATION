package com.example.demo.incident;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IncidentController.class)
class IncidentControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private IncidentRepository incidentRepository;

	@Test
	void getIncidentsReturnsRepositoryResults() throws Exception {
		Incident incident = new Incident();
		incident.setId(1L);
		incident.setIncidentName("Server outage");
		incident.setIncidentOwner("Jane Smith");
		incident.setIncidentOpenedBy("John Doe");
		incident.setIncidentSeverity("High");
		incident.setIncidentDate(LocalDate.of(2026, 6, 18));
		incident.setIncidentDetails("Primary database unavailable");
		incident.setIncidentNotes("Escalated to on-call engineer");

		when(incidentRepository.findAll()).thenReturn(List.of(incident));

		mockMvc.perform(get("/api/incidents"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].incidentName").value("Server outage"));
	}

	@Test
	void createIncidentPersistsAndReturnsCreatedIncident() throws Exception {
		Incident savedIncident = new Incident();
		savedIncident.setId(10L);
		savedIncident.setIncidentName("Server outage");
		savedIncident.setIncidentOwner("Jane Smith");
		savedIncident.setIncidentOpenedBy("John Doe");
		savedIncident.setIncidentSeverity("High");
		savedIncident.setIncidentDate(LocalDate.of(2026, 6, 18));
		savedIncident.setIncidentDetails("Primary database unavailable");
		savedIncident.setIncidentNotes("Escalated to on-call engineer");

		when(incidentRepository.save(any(Incident.class))).thenReturn(savedIncident);

		String requestBody = """
				{
				  "incidentName": "Server outage",
				  "incidentOwner": "Jane Smith",
				  "incidentOpenedBy": "John Doe",
				  "incidentSeverity": "High",
				  "incidentDate": "2026-06-18",
				  "incidentDetails": "Primary database unavailable",
				  "incidentNotes": "Escalated to on-call engineer"
				}
				""";

		mockMvc.perform(post("/api/incidents")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(10))
				.andExpect(jsonPath("$.incidentName").value("Server outage"));

		verify(incidentRepository).save(any(Incident.class));
	}

}
