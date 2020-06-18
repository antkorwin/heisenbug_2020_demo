package com.antkorwin.heisenbug.sales.service;

import java.util.List;

import com.antkorwin.heisenbug.sales.model.Car;
import com.antkorwin.heisenbug.sales.model.Item;

public interface ItemService {

	Item create(int price, Car car);
	List<Item> getAll();
}
