package com.example.reservationservice.service;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.dto.ItemDTO;
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
        Item item = Item.builder()
                .itemID(itemId)
                .cost(cost)
                .quantityAvailable(quantity)
                .status(Status.ACTIVE)
                .build();
        itemRepository.save(item);
        LOGGER.info("Item added successfully and is now available for reservation!");
        return new ResponseEntity<String>("Item added and available for reservation", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateItemCost(String itemId, float cost) {
        Item item = itemRepository.findByItemID(itemId);

        if ( item == null ) {
            LOGGER.info("Item not found");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        item.setCost(cost);
        itemRepository.save(item);
        LOGGER.info("Item cost updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> updateItemQuantity(String itemId, int quantity) {
        Item item = itemRepository.findByItemID(itemId);

        if ( item == null ) {
            LOGGER.info("Item not found");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        item.setQuantityAvailable(quantity);
        itemRepository.save(item);
        LOGGER.info("Item quantity updated successfully!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ItemDTO getItem(String itemId) {
        Item item = itemRepository.findByItemID(itemId);
        ItemDTO itemDTO;

        if (item==null) {
            itemDTO = ItemDTO.builder()
                            .response(new ResponseEntity<String>(HttpStatus.NOT_FOUND))
                            .build();
            LOGGER.info("Item not found");
        } else {
            itemDTO = ItemDTO.builder()
                    .itemID(item.getItemID())
                    .quantityAvailable(item.getQuantityAvailable())
                    .cost(item.getCost())
                    .updatedOn(item.getUpdatedOn())
                    .response(new ResponseEntity<>(HttpStatus.OK))
                    .build();
            LOGGER.info("Item has been found and returned successfully!");
        }
        return itemDTO;
    }

    public ResponseEntity<String> deleteItem( String itemId ) {
        Item item = itemRepository.findByItemID(itemId);

        if ( item == null ) {
            LOGGER.info("Item not found");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Item deleted successfully!");
        item.setStatus(Status.INACTIVE);
        itemRepository.save(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}