package com.example.reservationservice.repository;

import com.example.reservationservice.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item,String> {

    Item findByItemID(String itemID);
}
