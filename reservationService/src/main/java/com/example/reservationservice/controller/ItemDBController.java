package com.example.reservationservice.controller;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.model.Item;
import com.example.reservationservice.repository.ItemRepository;
import com.example.reservationservice.repository.ReservationRepository;
import com.example.reservationservice.service.ItemDBService;
import com.example.reservationservice.service.ItemDBServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
@EnableFeignClients
public class ItemDBController {

    @Autowired
    ItemDBService itemDBService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    @PostMapping("/item")
    public ResponseEntity<String> addItemInDB(@RequestParam String itemID, @RequestParam int quantity, @RequestParam float cost) {
        return itemDBService.addItem(itemID,quantity,cost);
    }

    @PutMapping("/cost")
    public ResponseEntity<String> updateItemCostInDB(@RequestParam String itemId, @RequestParam float cost) {
        return itemDBService.updateItemCost(itemId,cost);
    }

    @PutMapping("/quantity")
    public ResponseEntity<String> updateItemQuantityInDB(@RequestParam String itemId, @RequestParam int quantity) {
        return itemDBService.updateItemQuantity(itemId,quantity);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<String> getItemFromDB(@PathVariable String itemId) {
        return itemDBService.getItem(itemId);
    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deleteItemFromDB(@RequestParam String itemId, @RequestParam int quantity, @RequestParam float cost) {
        return itemDBService.deleteItem(itemId);
    }

}
