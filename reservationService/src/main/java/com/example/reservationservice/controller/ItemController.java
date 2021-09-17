package com.example.reservationservice.controller;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.dto.ItemDTO;
import com.example.reservationservice.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@EnableFeignClients
public class ItemController {

    @Autowired
    private ItemService itemService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    @PostMapping("/item")
    public ResponseEntity<String> addItem(@RequestParam String itemId, @RequestParam int quantity, @RequestParam float cost) {
        LOGGER.info("Starting Add Item Service.");
        return itemService.addItem(itemId,quantity,cost);
    }

    @PutMapping("/cost")
    public ResponseEntity<String> updateItemCost(@RequestParam String itemId, @RequestParam float cost) {
        LOGGER.info("Starting Update Item Cost Service.");
        return itemService.updateItemCost(itemId,cost);
    }

    @PutMapping("/quantity")
    public ResponseEntity<String> updateItemQuantity(@RequestParam String itemId, @RequestParam int quantity) {
        LOGGER.info("Starting Update Item Quantity Service.");
        return itemService.updateItemQuantity(itemId,quantity);
    }

    @GetMapping("/item/{itemId}")
    public ItemDTO getItem(@PathVariable String itemId) {
        LOGGER.info("Starting Get Item Service.");
        ItemDTO itemDTO = itemService.getItem(itemId);
        return itemDTO;
    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deleteItem(@RequestParam String itemId, @RequestParam int quantity, @RequestParam float cost) {
        LOGGER.info("Starting Delete Item Service.");
        return itemService.deleteItem(itemId);
    }

}
