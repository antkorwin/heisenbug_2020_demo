package com.antkorwin.heisenbug.sales.repository;


import com.antkorwin.heisenbug.sales.model.Item;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
