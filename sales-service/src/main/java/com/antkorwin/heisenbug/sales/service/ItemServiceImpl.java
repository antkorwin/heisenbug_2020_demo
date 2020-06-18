package com.antkorwin.heisenbug.sales.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.antkorwin.heisenbug.sales.model.Car;
import com.antkorwin.heisenbug.sales.model.Item;
import com.antkorwin.heisenbug.sales.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

	private final ItemRepository repository;

	@Override
	public Item create(int price, Car car) {

		while (price < 10000 ) {
			price = price *10;
		}

		Date plus3Days = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3);
		return repository.save(Item.builder()
		                           .car(car)
		                           .price(price)
		                           .createTime(new Date())
		                           .startSalesTime(plus3Days)
		                           .build());
	}

	@Override
	public List<Item> getAll() {
		List<Item> items = repository.findAll();
		return items;
	}
}
