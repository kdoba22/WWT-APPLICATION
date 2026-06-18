package com.example.demo.incident;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "incidents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Incident {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String incidentName;

	@Column(nullable = false)
	private String incidentOwner;

	@Column(nullable = false)
	private String incidentOpenedBy;

	@Column(nullable = false)
	private String incidentSeverity;

	@Column(nullable = false)
	private LocalDate incidentDate;

	@Column(nullable = false, length = 2000)
	private String incidentDetails;

	@Column(length = 2000)
	private String incidentNotes;

}
