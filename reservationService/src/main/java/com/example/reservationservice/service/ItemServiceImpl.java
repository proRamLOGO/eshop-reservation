package com.example.reservationservice.service;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.dto.ItemDTO;
import com.example.reservationservice.exception.ItemNotFoundException;
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
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    public ResponseEntity<String> addItem(String itemId, int quantity, float cost) {
        /*
         * Adds a new item to the database.
         * Params:  itemID - ID of item to be added
         *          quantity - Quantity available for reservation
         *          cost - Cost per item
         * Returns: Response on status of successful completion of operation.
         * */

        Item item = Item.builder()
                .itemId(itemId)
                .cost(cost)
                .quantityAvailable(quantity)
                .status(Status.ACTIVE)
                .build();
        itemRepository.save(item);
        LOGGER.info("Item added and available for reservation!");
        return new ResponseEntity<>("Item added and available for reservation", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateItemCost(String itemId, float cost) {
        /*
         * Updates cost per item of given item in the database.
         * Params:  itemID - ID of item.
         *          cost - new cost per item
         * Returns: Response on status of successful updation.
         * */

        Item item = itemRepository.findByItemId(itemId);

        if ( item == null ) {
            LOGGER.error("Item not found");
            return new ResponseEntity<>("ITEM NOT FOUND", HttpStatus.NOT_FOUND);
        }

        item.setCost(cost);
        itemRepository.save(item);
        LOGGER.info("Item cost updated");
        return new ResponseEntity<>("ITEM COST UPDATED", HttpStatus.OK);
    }

    public ResponseEntity<String> updateItemQuantity(String itemId, int quantity) {
        /*
         * Updates quantity of given item in the database.
         * Params:  itemID - ID of item.
         *          quantity - new quantity which is available for reservation.
         * Returns: Response on status of successful updation.
         * */
        Item item = itemRepository.findByItemId(itemId);

        if ( item == null ) {
            LOGGER.error("Item not found");
            return new ResponseEntity<>("ITEM NOT FOUND", HttpStatus.NOT_FOUND);
        }

        item.setQuantityAvailable(quantity);
        itemRepository.save(item);
        LOGGER.info("Item quantity updated");
        return new ResponseEntity<>("ITEM QUANTITY UPDATED", HttpStatus.OK);
    }

    public ItemDTO getItem(String itemId) {
        /*
         * Fetches requested item.
         * Params:  itemID - ID of item.
         * Returns: DTO with vital information of item.
         * */
        Item item = itemRepository.findByItemId(itemId);
        ItemDTO itemDTO;

        if (item==null) {
            itemDTO = null;
            LOGGER.error("Item not found");
        } else {
            itemDTO = ItemDTO.builder()
                    .itemId(item.getItemId())
                    .quantityAvailable(item.getQuantityAvailable())
                    .cost(item.getCost())
                    .updatedOn(item.getUpdatedOn())
                    .build();
            LOGGER.info("Item has been found and returned successfully!");
        }
        return itemDTO;
    }

    public ResponseEntity<String> deleteItem( String itemId ) {
        /*
         * Deletes item from database.
         * Params:  itemID - ID of item to be deleted.
         * Returns: Response on status of successful deletion.
         * */
        Item item = itemRepository.findByItemId(itemId);

        if ( item == null ) {
            LOGGER.error("Item not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Item deleted successfully!");
        item.setStatus(Status.INACTIVE);
        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}