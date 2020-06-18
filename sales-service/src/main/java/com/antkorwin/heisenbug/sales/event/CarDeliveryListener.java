package com.antkorwin.heisenbug.sales.event;


import com.antkorwin.heisenbug.contracts.event.CarEvent;
import com.antkorwin.heisenbug.sales.mapper.CarMapper;
import com.antkorwin.heisenbug.sales.model.Car;
import com.antkorwin.heisenbug.sales.service.ItemService;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarDeliveryListener {

	private final ItemService service;
	private final CarMapper carMapper;

	@RabbitListener(queues = "sales-queue")
	void onDeliveryNewCar(CarEvent event) {
		Car carDescription = carMapper.fromDto(event.getCar());
		int price = 57000;
		service.create(price, carDescription);
	}
}
