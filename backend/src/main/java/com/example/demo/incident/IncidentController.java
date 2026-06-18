package com.example.demo.incident;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

	private final IncidentRepository incidentRepository;

	public IncidentController(IncidentRepository incidentRepository) {
		this.incidentRepository = incidentRepository;
	}

	@GetMapping
	public List<Incident> getIncidents() {
		return incidentRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Incident createIncident(@RequestBody CreateIncidentRequest request) {
		Incident incident = new Incident();
		incident.setIncidentName(request.incidentName());
		incident.setIncidentOwner(request.incidentOwner());
		incident.setIncidentOpenedBy(request.incidentOpenedBy());
		incident.setIncidentSeverity(request.incidentSeverity());
		incident.setIncidentDate(request.incidentDate());
		incident.setIncidentDetails(request.incidentDetails());
		incident.setIncidentNotes(request.incidentNotes());
		return incidentRepository.save(incident);
	}

}
