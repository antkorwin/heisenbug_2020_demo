package com.antkorwin.heisenbug.sales.service;

import com.antkorwin.heisenbug.sales.model.Car;
import com.antkorwin.heisenbug.sales.model.Engine;
import com.jupiter.tools.spring.test.mongo.annotation.ExpectedMongoDataSet;
import com.jupiter.tools.spring.test.mongo.annotation.ExportMongoDataSet;
import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;
import com.jupiter.tools.spring.test.mongo.junit5.meta.annotation.MongoDbIntegrationTest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


@MongoDbIntegrationTest
class ItemServiceTest {

	@Autowired
	private ItemService itemService;


	@Test
	@ExpectedMongoDataSet("/datasets/export.json")
	void create() {
		itemService.create(570, Car.builder()
		                             .name("Camaro")
		                             .engine(Engine.builder()
		                                           .name("V8")
		                                           .volume(5.7f)
		                                           .horsePower(550)
		                                           .fuelType("GASOLINE")
		                                           .build())
		                             .build());
	}

	@Test
	@MongoDataSet(value = "/datasets/init__get_all.json",
	              cleanBefore = true, cleanAfter = true)
	void getAll() {
		assertThat(itemService.getAll()).hasSize(2);
	}
}