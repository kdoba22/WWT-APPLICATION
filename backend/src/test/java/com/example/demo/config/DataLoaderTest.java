package com.example.demo.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.incident.IncidentRepository;
import com.example.demo.person.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DataLoaderTest {

	@Autowired
	private IncidentRepository incidentRepository;

	@Autowired
	private PersonRepository personRepository;

	@Test
	void seedsSampleIncidentsAndPersonsOnStartup() {
		assertThat(incidentRepository.count()).isEqualTo(4);
		assertThat(personRepository.count()).isEqualTo(7);
	}

}
