package com.antkorwin.heisenbug.carfactory.model;


import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	private UUID id;

	private String name;
	private int year;

	@Column(unique = true)
	private String serialNumber;
	private Date dateOfConstruct;

	@ManyToOne
	@JoinColumn(name = "engine_id", nullable = false)
	private Engine engine;
}
