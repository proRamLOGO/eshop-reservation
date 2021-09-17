package com.example.reservationservice.service;

import com.example.reservationservice.dto.ItemDTO;
import org.springframework.http.ResponseEntity;

public interface ItemService {

    ResponseEntity<String> addItem(String itemID, int quantity, float cost);

    ResponseEntity<String> updateItemCost(String itemID, float cost);

    ResponseEntity<String> updateItemQuantity(String itemID, int quantity);

    ResponseEntity<String> deleteItem(String itemID);

    ItemDTO getItem(String itemID);

}
