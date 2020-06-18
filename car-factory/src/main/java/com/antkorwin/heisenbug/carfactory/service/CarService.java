package com.antkorwin.heisenbug.carfactory.service;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.antkorwin.heisenbug.carfactory.model.Car;
import com.antkorwin.heisenbug.carfactory.model.Engine;
import com.antkorwin.heisenbug.carfactory.repository.CarRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CarService {

	private final CarRepository carRepository;

	@Transactional
	public Car create(String name, Engine engine) {

		Car car = Car.builder()
		             .engine(engine)
		             .name(name)
		             .year(LocalDate.now().getYear())
		             .serialNumber(getNextNumber(name))
		             .dateOfConstruct(new Date())
		             .build();

		car =  carRepository.save(car);
		return car;
	}

	@Transactional(readOnly = true)
	public List<Car> getAllAfter(Date fromDate) {
		return carRepository.findAllByDateOfConstructAfter(fromDate);
	}

	@Transactional(readOnly = true)
	public Car get(UUID id) {
		return carRepository.findById(id)
		                    .orElseThrow(() -> new RuntimeException("Car not found"));
	}

	private String getNextNumber(String name) {
		String prefix = name.replace(" ", "-").toUpperCase();
		String number = UUID.randomUUID().toString().substring(0, 8);
		return "${prefix}-${number}";
	}
}
