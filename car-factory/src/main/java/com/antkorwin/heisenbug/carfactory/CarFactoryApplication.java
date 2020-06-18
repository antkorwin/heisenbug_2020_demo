package com.antkorwin.heisenbug.carfactory;

import com.antkorwin.spring.commons.banner.SpringBanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarFactoryApplication {

	public static void main(String[] args) {
		SpringBanner.print(() -> SpringApplication.run(CarFactoryApplication.class, args));
	}

}
