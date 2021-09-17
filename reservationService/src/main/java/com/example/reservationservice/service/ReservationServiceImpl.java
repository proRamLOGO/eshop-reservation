package com.example.reservationservice.service;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.dto.CreateReservationDTO;
import com.example.reservationservice.dto.ReservationDTO;
import com.example.reservationservice.dto.ReservationResponseDTO;
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

    public CreateReservationDTO createReservation(String itemID, int quantity) {

        Item item = itemRepository.findByItemID(itemID);
        CreateReservationDTO createReservationDTO = new CreateReservationDTO();

        if ( item == null ) {
            LOGGER.info("Reservation not Created because Item was not found!");
//            createReservationDTO.setResponse(new ResponseEntity<>("ITEM NOT FOUND", HttpStatus.NOT_FOUND));

        } else if ( quantity > item.getQuantityAvailable() ) {
            LOGGER.info("Reservation not Created because available Item quantity was less then demand!");
//            createReservationDTO.setResponse(new ResponseEntity<>("SHORTAGE OF QUANTITY", HttpStatus.EXPECTATION_FAILED));

        } else {

            float reservationCost = quantity * item.getCost();
            int newQuantityAvailable = item.getQuantityAvailable() - quantity;

            Reservation reservation = Reservation.builder()
                    .reservationID(UUID.randomUUID().toString())
                    .itemID(itemID)
                    .quantity(quantity)
                    .cost(reservationCost)
                    .status(Status.ACTIVE)
                    .build();

            item.setQuantityAvailable(newQuantityAvailable);

            reservationRepository.save(reservation);
            itemRepository.save(item);

            createReservationDTO.setReservationID(reservation.getReservationID());
            createReservationDTO.setCostPerItem(item.getCost());
//            createReservationDTO.setResponse(new ResponseEntity<>("RESERVATION CREATED", HttpStatus.CREATED));

            LOGGER.info("Reservation created successfully!");
        }

        return createReservationDTO;

    }

    public ReservationResponseDTO updateReservation(String reservationID, int newQuantity) {

        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        Reservation reservation = reservationRepository.findByReservationID(reservationID);

        if ( reservation == null ) {
            LOGGER.info("Reservation not found.");
            reservationResponseDTO.setResponse(new ResponseEntity<>("Reservation NOT FOUND", HttpStatus.NOT_FOUND));
            return reservationResponseDTO;

        } else if ( reservation.getStatus() == Status.INACTIVE ) {
            LOGGER.info("Reservation has been deleted.");
            reservationResponseDTO.setResponse(new ResponseEntity<>("Reservation DELETED", HttpStatus.GONE));
            return reservationResponseDTO;

        }

        String itemID = reservation.getItemID();
        Item item = itemRepository.findByItemID(itemID);

        int currentReservedQuantity = reservation.getQuantity();
        int quantityChange = newQuantity - currentReservedQuantity;

        if ( quantityChange > item.getQuantityAvailable() ) {
            LOGGER.info("Reservation not Updated because available Item quantity was less then demand!");
            reservationResponseDTO.setResponse(new ResponseEntity<>("SHORTAGE", HttpStatus.EXPECTATION_FAILED));

        } else {

            float reservationCost = newQuantity * item.getCost();
            int newQuantityAvailable = item.getQuantityAvailable() - quantityChange;

            reservation.setCost(reservationCost);
            reservation.setQuantity(newQuantity);

            item.setQuantityAvailable(newQuantityAvailable);

            reservationRepository.save(reservation);
            itemRepository.save(item);

            LOGGER.info("Reservation updated successfully!");
            reservationResponseDTO.setResponse(new ResponseEntity<>("UPDATED SUCCESSFULLY", HttpStatus.OK));

        }

        return reservationResponseDTO;

    }

    public ReservationResponseDTO deleteReservation(String reservationID) {

        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        Reservation reservation = reservationRepository.findByReservationID(reservationID);

        if ( reservation == null ) {
            LOGGER.info("Reservation not found.");
            reservationResponseDTO.setResponse(new ResponseEntity<>("Reservation NOT FOUND", HttpStatus.NOT_FOUND));
            return reservationResponseDTO;

        } else if ( reservation.getStatus() == Status.INACTIVE ) {
            LOGGER.info("Reservation has been deleted already.");
            reservationResponseDTO.setResponse(new ResponseEntity<>("Reservation DELETED ALREADY", HttpStatus.GONE));
            return reservationResponseDTO;

        }

        Item item = itemRepository.findByItemID(reservation.getItemID());

        reservation.setStatus(Status.INACTIVE);
        item.setQuantityAvailable(item.getQuantityAvailable() + reservation.getQuantity());

        reservationRepository.save(reservation);
        itemRepository.save(item);

        LOGGER.info("Reservation deleted successfully!");

        reservationResponseDTO.setResponse(new ResponseEntity<>("DELETED", HttpStatus.OK));

        return reservationResponseDTO;
    }

    public ReservationDTO getReservation(String reservationID) {

        Reservation reservation = reservationRepository.findByReservationID(reservationID);
        ReservationDTO reservationDTO = new ReservationDTO();

        if ( reservation == null || reservation.getStatus() == Status.INACTIVE ) {
            LOGGER.info("Reservation not found.");
            return null;
        }

        reservationDTO = ReservationDTO.builder()
                .reservationID(reservation.getReservationID())
                .itemID(reservation.getItemID())
                .quantity(reservation.getQuantity())
                .cost(reservation.getCost())
                .build();

        reservationDTO.setResponse(new ResponseEntity<>("RESERVATION FOUND", HttpStatus.OK ));

        LOGGER.info("Reservation found and returned successfully!");

        return reservationDTO;

    }

}
