package com.antkorwin.heisenbug.carfactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.antkorwin.heisenbug.carfactory.model.Car;
import com.antkorwin.heisenbug.carfactory.model.Engine;
import com.antkorwin.heisenbug.carfactory.model.FuelType;
import com.antkorwin.heisenbug.carfactory.notes.Bad;
import com.antkorwin.heisenbug.carfactory.notes.Good;
import com.antkorwin.heisenbug.carfactory.notes.Ugly;
import com.antkorwin.heisenbug.carfactory.repository.CarRepository;
import com.antkorwin.heisenbug.carfactory.repository.EngineRepository;
import com.antkorwin.heisenbug.carfactory.service.CarService;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.DataSetFormat;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import utils.TraceSql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("disable-jms")
@TraceSql
@DBRider
@SpringBootTest
class CarServiceTests {

	private static final UUID DIESEL_ENGINE_ID = UUID.fromString("d1ef755d-f593-4f90-98f0-e2f4e4b24faa");

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private EngineRepository engineRepository;

	@Autowired
	private CarService carService;


	// 1 - Создаем данные руками в java коде
	@Ugly("Много кода")
	@Test
	void creatingManually() {
		// Arrange
		Engine dieselEngine = engineRepository.save(Engine.builder()
		                                                  .fuelType(FuelType.DIESEL)
		                                                  .horsePower(160)
		                                                  .volume(4.2f)
		                                                  .build());
		Date before = new Date();
		// Act
		Car car = carService.create("Dodge Ram", dieselEngine);
		// Assert
		assertThat(car).isNotNull();
		assertThat(car.getSerialNumber()).matches("(DODGE-RAM-.{8})$");
		assertThat(car.getDateOfConstruct()).isBetween(before, new Date());
		assertThat(car.getYear()).isEqualTo(LocalDate.now().getYear());
		// teardown
		carRepository.delete(car);
		engineRepository.delete(dieselEngine);
	}


	// 2 - Пишем SQL руками
	@Bad("Недостаточно гибко")
	@Test
	@Sql(value = "/sql/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = "/sql/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void creatingFromSQL() {
		// Arrange
		Engine dieselEngine = engineRepository.getOne(DIESEL_ENGINE_ID);
		Date before = new Date();
		// Act
		Car car = carService.create("Dodge Ram", dieselEngine);
		// Assert
		assertThat(car).isNotNull();
		assertThat(car.getSerialNumber()).matches("(DODGE-RAM-.{8})$");
		assertThat(car.getDateOfConstruct()).isBetween(before, new Date());
		assertThat(car.getYear()).isEqualTo(LocalDate.now().getYear());
	}


	// 3 - Используем DbRider для наполнения данными базы
	@Bad("Все равно осталось много кода проверок")
	@Test
	@DataSet(value = "/datasets/engines.json",
	         cleanBefore = true, cleanAfter = true)
	void creatingWithDbRider() {
		// Arrange
		Date before = new Date();
		Engine engine = engineRepository.getOne(DIESEL_ENGINE_ID);
		// Act
		Car car = carService.create("Dodge Ram", engine);
		assertThat(car).isNotNull();
		assertThat(car.getSerialNumber()).matches("(DODGE-RAM-[a-f0-9]{8})$");
		assertThat(car.getDateOfConstruct()).isBetween(before, new Date());
		assertThat(car.getYear()).isEqualTo(LocalDate.now().getYear());
	}


	//3.1 - фича для ленивых
	@Good("Один раз воспользовались и в Ignore")
	@Test
	@DataSet(cleanBefore = true, cleanAfter = true)
	@ExportDataSet(outputName = "./target/dataset/export.json", format = DataSetFormat.JSON)
	void generateDataSet() {
		Engine dieselEngine = engineRepository.save(Engine.builder()
		                                                  .fuelType(FuelType.DIESEL)
		                                                  .horsePower(160)
		                                                  .volume(4.2f)
		                                                  .build());
		carService.create("Dodge Ram", dieselEngine);
	}


	// 4 - Expected data set
	@Good
	@Test
	@DataSet(value = "/datasets/engines.json",
	         cleanBefore = true, cleanAfter = true)
	@ExpectedDataSet("/datasets/create_car_expected.json")
	void createAndExpect() {

		Engine engine = engineRepository.getOne(DIESEL_ENGINE_ID);
		carService.create("Dodge Ram", engine);

	}


	// 5
	@Ugly("конца света не было")
	@Test
	@DataSet(value = "/datasets/get_newer_cars_init.json",
	         cleanAfter = true, cleanBefore = true)
	void findAllNewerThan_hardcoded() {
		Date now = new Date();
		List<Car> cars = carService.getAllAfter(now);
		assertThat(cars).isNotNull()
		                .hasSize(1)
		                .extracting(Car::getName)
		                .containsExactly("Bat-mobile");
	}


	// 6
	@Good("Динамика в init дата сэтах")
	@Test
	@DataSet(value = "/datasets/get_newer_cars_smartdates_init.json",
	         cleanAfter = true, cleanBefore = true)
	void findAllNewerThan() {
		Date now = new Date(System.currentTimeMillis() - 60_000);
		List<Car> cars = carService.getAllAfter(now);
		assertThat(cars).isNotNull()
		                .hasSize(1)
		                .extracting(Car::getName)
		                .containsExactly("Bat-mobile");
	}
}
