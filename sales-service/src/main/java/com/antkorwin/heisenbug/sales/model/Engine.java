package com.antkorwin.heisenbug.sales.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Engine {
	private String name;
	private float volume;
	private int horsePower;
	private String fuelType;
}
