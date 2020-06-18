package com.antkorwin.heisenbug.sales.event;

import java.util.concurrent.TimeUnit;

import com.antkorwin.heisenbug.contracts.event.CarEvent;
import com.antkorwin.heisenbug.contracts.event.EventType;
import com.antkorwin.heisenbug.contracts.event.dto.CarDto;
import com.antkorwin.heisenbug.contracts.event.dto.EngineDto;
import com.antkorwin.heisenbug.sales.model.Car;
import com.antkorwin.heisenbug.sales.model.Engine;
import com.antkorwin.heisenbug.sales.service.ItemService;
import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.meta.annotation.MongoDbIntegrationTest;
import com.jupiter.tools.spring.test.rabbitmq.annotation.meta.EnableRabbitMqTest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@Disabled("более честная альтернатива -> CarDeliveryListener_FullTest")
@MongoDbIntegrationTest
@EnableRabbitMqTest
class CarDeliveryListener_MockBeanTest {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@MockBean
	private ItemService itemService;

	private ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);

	@Test
	@MongoDataSet(cleanAfter = true, cleanBefore = true)
	void sendEvent() {
		// Send event:
		amqpTemplate.convertAndSend("sales-queue", getEvent());
		// Await:
		Awaitility.await()
		          .atMost(3, TimeUnit.SECONDS)
		          .untilAsserted(() -> verify(itemService).create(eq(57000),
		                                                          captor.capture()));
		// Assert:
		assertThat(captor.getValue()).extracting(Car::getName)
		                             .isEqualTo("Camaro");

		assertThat(captor.getValue().getEngine()).extracting(Engine::getName,
		                                                     Engine::getFuelType,
		                                                     Engine::getHorsePower,
		                                                     Engine::getVolume)
		                                         .containsExactly("V6",
		                                                          "GASOLINE",
		                                                          420,
		                                                          6.2f);
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