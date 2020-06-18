package com.antkorwin.heisenbug.carfactory.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.antkorwin.heisenbug.carfactory.model.Car;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepository extends JpaRepository<Car, UUID> {

	List<Car> findAllByDateOfConstructAfter(Date fromDate);
}
