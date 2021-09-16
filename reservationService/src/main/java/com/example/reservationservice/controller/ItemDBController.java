package com.example.reservationservice.controller;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.model.Item;
import com.example.reservationservice.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemDBController {

    @Autowired
    ItemRepository itemsRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);


    @PostMapping("/item")
    public ResponseEntity<String> addItemInDB(@RequestParam String itemID, @RequestParam int quantity, @RequestParam float cost) {
        Item item = Item.builder()
                .itemID(itemID)
                .cost(cost)
                .quantityAvailable(quantity)
                .build();
        itemsRepository.save(item);
        return new ResponseEntity<>("Item ADDED", HttpStatus.CREATED);
    }

    @PutMapping("/cost")
    public ResponseEntity<String> updateItemCostInDB(@RequestParam String itemId, @RequestParam float cost) {
        Item item = itemsRepository.findByItemID(itemId);
        item.setCost(cost);
        itemsRepository.save(item);
        return new ResponseEntity<>("Cost UPDATED", HttpStatus.OK);
    }

    @PutMapping("/quantity")
    public ResponseEntity<String> updateItemQuantityInDB(@RequestParam String itemId, @RequestParam int quantity) {
        Item item = itemsRepository.findByItemID(itemId);
        item.setQuantityAvailable(quantity);
        itemsRepository.save(item);
        return new ResponseEntity<>("Quantity UPDATED", HttpStatus.OK);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<String> getItemFromDB(@PathVariable String itemId) {
        Item item = itemsRepository.findByItemID(itemId);
        itemsRepository.save(item);
        return new ResponseEntity<>("GOT Quantity", HttpStatus.OK);
    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deleteItemFromDB(@RequestParam String itemID, @RequestParam int quantity, @RequestParam float cost) {
        Item item = Item.builder()
                .itemID(itemID)
                .cost(cost)
                .quantityAvailable(quantity)
                .build();
        itemsRepository.save(item);
        return new ResponseEntity<>("GOT DELETED", HttpStatus.OK);
    }

}
