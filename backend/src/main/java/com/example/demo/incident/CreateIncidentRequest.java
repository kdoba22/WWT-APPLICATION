package com.example.demo.incident;

import java.time.LocalDate;

public record CreateIncidentRequest(
		String incidentName,
		String incidentOwner,
		String incidentOpenedBy,
		String incidentSeverity,
		LocalDate incidentDate,
		String incidentDetails,
		String incidentNotes
) {}
