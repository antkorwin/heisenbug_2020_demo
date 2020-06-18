package com.antkorwin.heisenbug.carfactory.model;


import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Engine {

	@Id
	@GeneratedValue
	@Type(type = "uuid-char")
	private UUID id;

	private String name;
	private float volume;
	private int horsePower;

	@Enumerated(EnumType.STRING)
	private FuelType fuelType;
}
