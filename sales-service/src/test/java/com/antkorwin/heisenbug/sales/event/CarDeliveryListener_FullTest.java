package com.antkorwin.heisenbug.sales.event;

import java.util.concurrent.TimeUnit;

import com.antkorwin.heisenbug.contracts.event.CarEvent;
import com.antkorwin.heisenbug.contracts.event.EventType;
import com.antkorwin.heisenbug.contracts.event.dto.CarDto;
import com.antkorwin.heisenbug.contracts.event.dto.EngineDto;
import com.antkorwin.heisenbug.sales.repository.ItemRepository;
import com.jupiter.tools.spring.test.mongo.annotation.ExpectedMongoDataSet;
import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.meta.annotation.MongoDbIntegrationTest;
import com.jupiter.tools.spring.test.rabbitmq.annotation.meta.EnableRabbitMqTest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;


@MongoDbIntegrationTest
@EnableRabbitMqTest
class CarDeliveryListener_FullTest {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private ItemRepository itemRepository;


	@Test
	@MongoDataSet(cleanAfter = true, cleanBefore = true)
	@ExpectedMongoDataSet("/datasets/expected__delivery_car.json" /* Assert */)
	void sendEvent() {
		// Send event:
		amqpTemplate.convertAndSend("sales-queue", getEvent());
		// Await:
		Awaitility.await()
		          .atMost(3, TimeUnit.SECONDS)
		          .until(() -> itemRepository.count() > 0);
	}

	private CarEvent getEvent() {
		return CarEvent.builder()
		               .type(EventType.CREATE_NEW_CAR)
		               .car(CarDto.builder()
		                          .name("Camaro")
		                          .engine(EngineDto.builder()
		                                           .horsePower(420)
		                                           .name("V6")
		                                           .volume(6.2f)
		                                           .fuelType("GASOLINE")
		                                           .build())
		                          .build())
		               .build();
	}
}