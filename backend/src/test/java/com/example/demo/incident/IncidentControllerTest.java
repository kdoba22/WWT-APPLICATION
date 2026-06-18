package com.example.demo.incident;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class IncidentControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	void getIncidentsReturnsIncidentList() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		mockMvc.perform(get("/api/incidents"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(0)));
	}

	@Test
	void createAndFetchIncident() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.incidentName").value("Server outage"))
				.andExpect(jsonPath("$.incidentOwner").value("Jane Smith"))
				.andExpect(jsonPath("$.incidentOpenedBy").value("John Doe"))
				.andExpect(jsonPath("$.incidentSeverity").value("High"))
				.andExpect(jsonPath("$.incidentDate").value("2026-06-18"))
				.andExpect(jsonPath("$.incidentDetails").value("Primary database unavailable"))
				.andExpect(jsonPath("$.incidentNotes").value("Escalated to on-call engineer"));

		mockMvc.perform(get("/api/incidents"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[?(@.incidentName == 'Server outage')]").exists());
	}

}
