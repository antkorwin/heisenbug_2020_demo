package com.antkorwin.heisenbug.contracts.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EngineDto {
	private String name;
	private float volume;
	private int horsePower;
	private String fuelType;
}
