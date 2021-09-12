package com.example.reservationservice.repository;

import com.example.reservationservice.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemsRepository extends MongoRepository<Item,String> {

    Item findByItemID(String itemID);
}
