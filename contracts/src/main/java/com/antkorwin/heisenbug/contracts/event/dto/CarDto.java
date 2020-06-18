package com.antkorwin.heisenbug.contracts.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
	private String name;
	private EngineDto engine;
}
