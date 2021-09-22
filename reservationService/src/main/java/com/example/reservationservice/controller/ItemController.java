package com.example.reservationservice.controller;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.dto.ItemDTO;
import com.example.reservationservice.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * ItemController Class
 * @Author : Shubh Bansal
 *
 * */
@RestController
@RequestMapping("/items")
@EnableFeignClients
public class ItemController {

    @Autowired
    private ItemService itemService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    @PostMapping("/item")
    public ResponseEntity<String> addItem(@RequestParam String itemId, @RequestParam int quantity, @RequestParam float cost) {
        /*
         * Uses item service to add a new item to the database.
         * Params:  itemID - ID of item
         *          quantity - Quantity that will be available for reservation
         *          cost - Cost per item
         * Returns: Response on status of successful completion of operation.
         * */
        LOGGER.info("Starting Add Item Service.");
        return itemService.addItem(itemId,quantity,cost);
    }

    @PutMapping("/cost")
    public ResponseEntity<String> updateItemCost(@RequestParam String itemId, @RequestParam float cost) {
        /*
         * Uses item service to update cost per item of given item in the database.
         * Params:  itemID - ID of item.
         *          cost - new cost per item
         * Returns: Response on status of successful updation.
         * */
        LOGGER.info("Starting Update Item Cost Service.");
        return itemService.updateItemCost(itemId,cost);
    }

    @PutMapping("/quantity")
    public ResponseEntity<String> updateItemQuantity(@RequestParam String itemId, @RequestParam int quantity) {
        /*
         * Uses item service to update quantity of given item in the database.
         * Params:  itemID - ID of item.
         *          quantity - new quantity which is available for reservation.
         * Returns: Response on status of successful updation.
         * */
        LOGGER.info("Starting Update Item Quantity Service.");
        return itemService.updateItemQuantity(itemId,quantity);
    }

    @GetMapping("/item/{itemId}")
    public ItemDTO getItem(@PathVariable String itemId) {
        /*
         * Uses item service to fetch item.
         * Params:  itemID - ID of item.
         * Returns: DTO with vital information of item.
         * */
        LOGGER.info("Starting Get Item Service.");
        ItemDTO itemDTO = itemService.getItem(itemId);
        return itemDTO;
    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deleteItem(@RequestParam String itemId) {
        /*
         * Uses item service to delete item from database.
         * Params:  itemID - ID of item to be deleted.
         * Returns: Response on status of successful deletion.
         * */
        LOGGER.info("Starting Delete Item Service.");
        return itemService.deleteItem(itemId);
    }

}
