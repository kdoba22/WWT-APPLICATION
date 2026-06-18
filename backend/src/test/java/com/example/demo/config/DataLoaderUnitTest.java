package com.example.demo.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.incident.Incident;
import com.example.demo.incident.IncidentRepository;
import com.example.demo.person.Person;
import com.example.demo.person.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.DefaultApplicationArguments;

@ExtendWith(MockitoExtension.class)
class DataLoaderUnitTest {

	@Mock
	private IncidentRepository incidentRepository;

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private DataLoader dataLoader;

	@Test
	void doesNotSeedWhenIncidentsAlreadyExist() {
		when(incidentRepository.count()).thenReturn(2L);

		dataLoader.run(new DefaultApplicationArguments(new String[0]));

		verify(incidentRepository, never()).save(any(Incident.class));
		verify(personRepository, never()).save(any(Person.class));
	}

	@Test
	void seedsIncidentsAndPersonsWhenDatabaseIsEmpty() {
		when(incidentRepository.count()).thenReturn(0L);
		when(incidentRepository.save(any(Incident.class))).thenAnswer(invocation -> {
			Incident incident = invocation.getArgument(0);
			incident.setId(1L);
			return incident;
		});

		dataLoader.run(new DefaultApplicationArguments(new String[0]));

		verify(incidentRepository, times(4)).save(any(Incident.class));
		verify(personRepository, times(7)).save(any(Person.class));
	}

}
