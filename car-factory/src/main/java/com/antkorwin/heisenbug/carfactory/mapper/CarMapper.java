package com.antkorwin.heisenbug.carfactory.mapper;

import com.antkorwin.heisenbug.carfactory.model.Car;
import com.antkorwin.heisenbug.contracts.event.dto.CarDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

	CarDto toDto(Car car);
}
