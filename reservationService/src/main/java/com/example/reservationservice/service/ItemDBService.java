package com.example.reservationservice.service;

import org.springframework.http.ResponseEntity;

public interface ItemDBService {

    public ResponseEntity<String> addItem(String itemID, int quantity, float cost);

    public ResponseEntity<String> updateItemCost(String itemID, int newCost);

    public ResponseEntity<String> updateItemQuantity(String itemID, int newQuantity);

    public ResponseEntity<String> deleteItem(String itemID);

    public ResponseEntity<String> getItem(String itemID);

}
