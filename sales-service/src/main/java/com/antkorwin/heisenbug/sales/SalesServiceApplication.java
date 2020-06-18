package com.antkorwin.heisenbug.sales;

import com.antkorwin.spring.commons.banner.SpringBanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SalesServiceApplication {

	public static void main(String[] args) {
		SpringBanner.print(() -> SpringApplication.run(SalesServiceApplication.class, args));
	}
}
