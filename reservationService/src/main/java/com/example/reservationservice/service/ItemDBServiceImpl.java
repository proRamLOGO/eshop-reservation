package com.example.reservationservice.service;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.model.Item;
import com.example.reservationservice.model.Status;
import com.example.reservationservice.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ItemDBServiceImpl implements ItemDBService {

    @Autowired
    ItemRepository itemRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    public ResponseEntity<String> addItem(String itemId, int quantity, float cost) {
        Item item = Item.builder()
                .itemID(itemId)
                .cost(cost)
                .quantityAvailable(quantity)
                .status(Status.ACTIVE)
                .build();
        itemRepository.save(item);
        return new ResponseEntity<>("Item ADDED", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateItemCost(String itemId, float cost) {
        Item item = itemRepository.findByItemID(itemId);
        item.setCost(cost);
        itemRepository.save(item);
        return new ResponseEntity<>("Cost UPDATED", HttpStatus.OK);
    }

    public ResponseEntity<String> updateItemQuantity(String itemId, int quantity) {
        Item item = itemRepository.findByItemID(itemId);
        item.setQuantityAvailable(quantity);
        itemRepository.save(item);
        return new ResponseEntity<>("Quantity UPDATED", HttpStatus.OK);
    }

    public ResponseEntity<String> getItem(String itemId) {
        Item item = itemRepository.findByItemID(itemId);
        if (item==null) {
            return new ResponseEntity<>("ITEM NOT FOUND", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("ITEM FOUND", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteItem( String itemId ) {
        Item item = itemRepository.findByItemID(itemId);
        item.setStatus(Status.INACTIVE);
        itemRepository.save(item);
        return new ResponseEntity<>("ITEM DELETED", HttpStatus.OK);
    }

}