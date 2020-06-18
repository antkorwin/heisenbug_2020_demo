package com.antkorwin.heisenbug.sales.mapper;


import com.antkorwin.heisenbug.contracts.event.dto.CarDto;
import com.antkorwin.heisenbug.sales.model.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

	CarDto toDto(Car car);

	Car fromDto(CarDto car);
}
