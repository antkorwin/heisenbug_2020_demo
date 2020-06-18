package com.antkorwin.heisenbug.carfactory;

import com.antkorwin.heisenbug.carfactory.usecase.DeliveryNewCar;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.jupiter.tools.spring.test.rabbitmq.annotation.ExpectedMessages;
import com.jupiter.tools.spring.test.rabbitmq.annotation.meta.EnableRabbitMqTest;
import org.junit.jupiter.api.Test;
import utils.TraceSql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@TraceSql
@DBRider
@SpringBootTest
@EnableRabbitMqTest
class DeliveryNewCarTest {

	@Autowired
	private DeliveryNewCar deliveryNewCar;

	@Test
	@DataSet(value = "/datasets/engines.json", cleanBefore = true, cleanAfter = true)
	@ExpectedMessages(queue = "sales-queue", messagesFile = "/datasets/expected_messages.json", timeout = 100)
	void createAndExpect() {
		deliveryNewCar.create("baNaNa", "V6-Turbo");
	}
}
