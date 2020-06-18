package com.antkorwin.heisenbug.carfactory.usecase;




import com.antkorwin.heisenbug.carfactory.mapper.CarMapper;
import com.antkorwin.heisenbug.carfactory.model.Car;
import com.antkorwin.heisenbug.carfactory.model.Engine;
import com.antkorwin.heisenbug.carfactory.repository.EngineRepository;
import com.antkorwin.heisenbug.carfactory.service.CarService;
import com.antkorwin.heisenbug.contracts.event.CarEvent;
import com.antkorwin.heisenbug.contracts.event.EventType;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class DeliveryNewCar {

	private final CarService carService;
	private final EngineRepository engineRepository;
	private final CarMapper mapper;
	private final AmqpTemplate amqpTemplate;

	@Transactional
	public void create(String carName, String engineName) {

		Engine engine = engineRepository.findByName(engineName);
		Car car = carService.create(carName, engine);
		CarEvent event = CarEvent.builder()
		                         .car(mapper.toDto(car))
		                         .type(EventType.CREATE_NEW_CAR)
		                         .build();

		amqpTemplate.convertAndSend("sales-queue", event);
	}
}
