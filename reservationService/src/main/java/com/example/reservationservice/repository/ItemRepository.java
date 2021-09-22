package com.example.reservationservice.repository;

import com.example.reservationservice.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*
 * ItemRepository Repository
 * @Author : Shubh Bansal
 * Database: MongoDB
 *
 * */
@Repository
public interface ItemRepository extends MongoRepository<Item,String> {

    /*
    * Finds and returns a unique Item from 'items' table with given itemId.
    * Params:  itemId - item requested
    * Returns: Item Object for corresponding item
    * */
    Item findByItemId(String itemId);
}
