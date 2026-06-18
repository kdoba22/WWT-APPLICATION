package com.example.demo.config;

import com.example.demo.incident.Incident;
import com.example.demo.incident.IncidentRepository;
import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;
import java.time.LocalDate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

	private final IncidentRepository incidentRepository;
	private final PersonRepository personRepository;

	public DataLoader(IncidentRepository incidentRepository, PersonRepository personRepository) {
		this.incidentRepository = incidentRepository;
		this.personRepository = personRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (incidentRepository.count() > 0) {
			return;
		}

		Incident serverOutage = createIncident(
				"Server outage",
				"Jane Smith",
				"John Doe",
				"High",
				LocalDate.of(2026, 6, 15),
				"Primary database unavailable for 45 minutes",
				"Failover completed and services restored");
		Incident loginFailure = createIncident(
				"Login failure spike",
				"Michael Chen",
				"Sarah Lopez",
				"Medium",
				LocalDate.of(2026, 6, 16),
				"Authentication service returning intermittent 503 errors",
				"Monitoring increased error rate on auth cluster");
		Incident paymentDelay = createIncident(
				"Payment processing delay",
				"Aisha Patel",
				"Tom Baker",
				"Critical",
				LocalDate.of(2026, 6, 17),
				"Payment queue backlog exceeded SLA threshold",
				"Escalated to payments on-call team");
		Incident emailDelay = createIncident(
				"Email notification delay",
				"Emily Davis",
				"Chris Nguyen",
				"Low",
				LocalDate.of(2026, 6, 18),
				"Transactional emails delayed by up to 20 minutes",
				"Vendor reported regional SMTP latency");

		createPerson("John Doe", serverOutage);
		createPerson("Jane Smith", serverOutage);
		createPerson("Sarah Lopez", loginFailure);
		createPerson("Michael Chen", loginFailure);
		createPerson("Tom Baker", paymentDelay);
		createPerson("Aisha Patel", paymentDelay);
		createPerson("Chris Nguyen", emailDelay);
	}

	private Incident createIncident(
			String name,
			String owner,
			String openedBy,
			String severity,
			LocalDate date,
			String details,
			String notes) {
		Incident incident = new Incident();
		incident.setIncidentName(name);
		incident.setIncidentOwner(owner);
		incident.setIncidentOpenedBy(openedBy);
		incident.setIncidentSeverity(severity);
		incident.setIncidentDate(date);
		incident.setIncidentDetails(details);
		incident.setIncidentNotes(notes);
		return incidentRepository.save(incident);
	}

	private void createPerson(String userName, Incident incident) {
		Person person = new Person();
		person.setUserName(userName);
		person.setIncident(incident);
		personRepository.save(person);
	}

}
