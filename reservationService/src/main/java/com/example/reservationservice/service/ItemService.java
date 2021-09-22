package com.example.reservationservice.service;

import com.example.reservationservice.dto.ItemDTO;
import org.springframework.http.ResponseEntity;

/*
 * ItemService Interface
 * @Author : Shubh Bansal
 *
 * */
public interface ItemService {

    /*
     * Adds a new item to the database.
     * Params:  itemID - ID of item to be added
     *          quantity - Quantity available for reservation
     *          cost - Cost per item
     * Returns: Response on status of successful completion of operation.
     * */
    ResponseEntity addItem(String itemId, int quantity, float cost);


    /*
     * Updates cost per item of given item in the database.
     * Params:  itemID - ID of item.
     *          cost - new cost per item
     * Returns: Response on status of successful updation.
     * */
    ResponseEntity updateItemCost(String itemId, float cost);


    /*
     * Updates quantity of given item in the database.
     * Params:  itemID - ID of item.
     *          quantity - new quantity which is available for reservation.
     * Returns: Response on status of successful updation.
     * */
    ResponseEntity updateItemQuantity(String itemId, int quantity);


    /*
     * Fetches requested item.
     * Params:  itemID - ID of item.
     * Returns: DTO with vital information of item.
     * */
    ItemDTO getItem(String itemId);


    /*
     * Deletes item from database.
     * Params:  itemID - ID of item to be deleted.
     * Returns: Response on status of successful deletion.
     * */
    ResponseEntity deleteItem(String itemId);

}
