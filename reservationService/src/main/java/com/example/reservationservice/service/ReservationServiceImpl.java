package com.example.reservationservice.service;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.dto.CreateReservationDTO;
import com.example.reservationservice.dto.ReservationDTO;
import com.example.reservationservice.model.Item;
import com.example.reservationservice.model.Reservation;
import com.example.reservationservice.model.Status;
import com.example.reservationservice.repository.ItemRepository;
import com.example.reservationservice.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ItemRepository itemRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    public CreateReservationDTO createReservation(String itemId, int quantity) {
        /*
         * Creates a reservation with given quantity for the given item.
         * Params:  itemID - ID of item for which reservation needs to be created
         *          quantity - desired quantity which needs to be reserved
         * Returns: A DTO containing the Reservation ID of created reservation and cost per item
         * */

        Item item = itemRepository.findByItemId(itemId);
        CreateReservationDTO createReservationDTO = new CreateReservationDTO();

        if ( item == null ) {
            LOGGER.error("Item not found!");
            createReservationDTO = null;

        } else if ( quantity > item.getQuantityAvailable() ) {
            LOGGER.error("Requested quantity not available!");
            createReservationDTO = null;

        } else {

            float reservationCost = quantity * item.getCost();
            int newQuantityAvailable = item.getQuantityAvailable() - quantity;

            Reservation reservation = Reservation.builder()
                    .reservationId(UUID.randomUUID().toString())
                    .itemId(itemId)
                    .quantity(quantity)
                    .cost(reservationCost)
                    .status(Status.ACTIVE)
                    .build();

            item.setQuantityAvailable(newQuantityAvailable);

            reservationRepository.save(reservation);
            itemRepository.save(item);

            createReservationDTO.setReservationId(reservation.getReservationId());
            createReservationDTO.setCostPerItem(item.getCost());

            LOGGER.info("Reservation created successfully!");
        }

        return createReservationDTO;

    }

    public ResponseEntity updateReservation(String reservationId, int newQuantity) {
        /*
         * Updates a reservation with given quantity for the given item.
         * Params:  reservationId - ID of reservation which needs to be updated
         *          newQuantity - desired quantity which needs to be reserved after updation
         * Returns: Response on the successful completion of updation
         * */

        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        ResponseEntity<String> response;

        if ( reservation == null ) {
            LOGGER.error("Reservation not found.");
            response = new ResponseEntity<>("Reservation NOT FOUND", HttpStatus.NOT_FOUND);
            return response;

        } else if ( reservation.getStatus() == Status.INACTIVE ) {
            LOGGER.error("Reservation has been deleted.");
            response = new ResponseEntity<>("Reservation DELETED", HttpStatus.GONE);
            return response;

        }

        String itemId = reservation.getItemId();
        Item item = itemRepository.findByItemId(itemId);

        int currentReservedQuantity = reservation.getQuantity();
        int quantityChange = newQuantity - currentReservedQuantity;

        if (quantityChange > item.getQuantityAvailable()) {
            LOGGER.error("Requested quantity not available!");
            response = new ResponseEntity<>("SHORTAGE", HttpStatus.EXPECTATION_FAILED);

        } else {

            float reservationCost = newQuantity * item.getCost();
            int newQuantityAvailable = item.getQuantityAvailable() - quantityChange;

            reservation.setCost(reservationCost);
            reservation.setQuantity(newQuantity);

            item.setQuantityAvailable(newQuantityAvailable);

            reservationRepository.save(reservation);
            itemRepository.save(item);

            response = new ResponseEntity<>("UPDATED SUCCESSFULLY", HttpStatus.OK);

            LOGGER.info("Reservation updated successfully!");
        }

        return response;

    }

    public ResponseEntity deleteReservation(String reservationId) {
        /*
         * Deletes a reservation.
         * Params:  reservationId - ID of reservation which needs to be deleted
         * Returns: Response on the successful completion of deletion
         * */

        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        ResponseEntity<String> response;

        if ( reservation == null ) {
            LOGGER.error("Reservation not found.");
            response = new ResponseEntity<>("Reservation NOT FOUND", HttpStatus.NOT_FOUND);
            return response;

        } else if ( reservation.getStatus() == Status.INACTIVE ) {
            LOGGER.error("Reservation has been deleted already.");
            response = new ResponseEntity<>("Reservation DELETED ALREADY", HttpStatus.GONE);
            return response;

        }

        Item item = itemRepository.findByItemId(reservation.getItemId());

        reservation.setStatus(Status.INACTIVE);
        item.setQuantityAvailable(item.getQuantityAvailable() + reservation.getQuantity());

        reservationRepository.save(reservation);
        itemRepository.save(item);

        LOGGER.info("Reservation deleted successfully!");

        response = new ResponseEntity<>("DELETED", HttpStatus.OK);
        return response;

    }

    public ReservationDTO getReservation(String reservationId) {
        /*
         * Fetches requested reservation.
         * Params:  reservationId - ID of reservation.
         * Returns: DTO with vital information of reservation.
         * */

        Reservation reservation = reservationRepository.findByReservationId(reservationId);
        ReservationDTO reservationDTO;

        if ( reservation == null || reservation.getStatus() == Status.INACTIVE ) {

            reservationDTO = null;
            LOGGER.error("Reservation not found.");

        } else {

            reservationDTO = ReservationDTO.builder()
                    .reservationId(reservation.getReservationId())
                    .itemId(reservation.getItemId())
                    .quantity(reservation.getQuantity())
                    .cost(reservation.getCost())
                    .build();

            LOGGER.info("Reservation found and returned successfully!");

        }
        return reservationDTO;

    }

}
