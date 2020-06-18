package com.antkorwin.heisenbug.contracts.event;


import com.antkorwin.heisenbug.contracts.event.dto.CarDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarEvent {
	private EventType type;
	private CarDto car;
}
