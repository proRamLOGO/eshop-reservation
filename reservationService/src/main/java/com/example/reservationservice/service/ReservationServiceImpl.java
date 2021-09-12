package com.example.reservationservice.service;

import com.example.reservationservice.dto.CreateReservationDTO;
import com.example.reservationservice.dto.ReservationDTO;
import com.example.reservationservice.dto.ReservationResponseDTO;
import com.example.reservationservice.model.Item;
import com.example.reservationservice.model.Reservation;
import com.example.reservationservice.model.Status;
import com.example.reservationservice.repository.ItemsRepository;
import com.example.reservationservice.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationsRepository reservationsRepository;

    @Autowired
    ItemsRepository itemsRepository;

    public CreateReservationDTO createReservation(String itemID, int quantity) {

        Item item = itemsRepository.findByItemID(itemID);
        CreateReservationDTO createReservationDTO = new CreateReservationDTO();

        if ( item == null ) {
            // item with itemID NOT FOUND
            createReservationDTO.setResponse(new ResponseEntity<>("ITEM NOT FOUND", HttpStatus.NOT_FOUND));

        } else if ( quantity > item.getQuantityAvailable() ) {
            // Shortage of Supply, 406, 413, 409, 416, 417
            createReservationDTO.setResponse(new ResponseEntity<>("SHORTAGE", HttpStatus.EXPECTATION_FAILED));

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

            reservationsRepository.save(reservation);
            itemsRepository.save(item);

            createReservationDTO.setReservationID(reservation.getReservationID());
            createReservationDTO.setCostPerItem(item.getCost());
            createReservationDTO.setResponse(new ResponseEntity<>("CREATED", HttpStatus.CREATED));

        }

        return createReservationDTO;

    }

    public ReservationResponseDTO updateReservation(String reservationID, int newQuantity) {

        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        Reservation reservation = reservationsRepository.findByReservationID(reservationID);

        if ( reservation == null ) {
            // Reservation with ReservationID not found.
            reservationResponseDTO.setResponse(new ResponseEntity<>("Reservation NOT FOUND", HttpStatus.NOT_FOUND));
            return reservationResponseDTO;

        } else if ( reservation.getStatus() == Status.INACTIVE ) {
            // Reservation with ReservationID DELETED.
            reservationResponseDTO.setResponse(new ResponseEntity<>("Reservation DELETED", HttpStatus.GONE));
            return reservationResponseDTO;

        }

        String itemID = reservation.getItemID();
        Item item = itemsRepository.findByItemID(itemID);

        int currentReservedQuantity = reservation.getQuantity();
        int quantityChange = newQuantity - currentReservedQuantity;

        if ( quantityChange > item.getQuantityAvailable() ) {
            // Shortage of Supply, 406, 413, 409, 416, 417
            reservationResponseDTO.setResponse(new ResponseEntity<>("SHORTAGE", HttpStatus.EXPECTATION_FAILED));

        } else {

            float reservationCost = newQuantity * item.getCost();
            int newQuantityAvailable = item.getQuantityAvailable() - quantityChange;

            reservation.setCost(reservationCost);
            reservation.setQuantity(newQuantity);

            item.setQuantityAvailable(newQuantityAvailable);

            reservationsRepository.save(reservation);
            itemsRepository.save(item);

            reservationResponseDTO.setResponse(new ResponseEntity<>("UPDATED SUCCESSFULLY", HttpStatus.OK));

        }

        return reservationResponseDTO;

    }

    public ReservationResponseDTO deleteReservation(String reservationID) {

        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();
        Reservation reservation = reservationsRepository.findByReservationID(reservationID);

        if ( reservation == null ) {
            // Reservation with ReservationID not found.
            reservationResponseDTO.setResponse(new ResponseEntity<>("Reservation NOT FOUND", HttpStatus.NOT_FOUND));
            return reservationResponseDTO;

        } else if ( reservation.getStatus() == Status.INACTIVE ) {
            // Reservation with ReservationID DELETED.
            reservationResponseDTO.setResponse(new ResponseEntity<>("Reservation DELETED ALREADY", HttpStatus.GONE));
            return reservationResponseDTO;

        }

        Item item = itemsRepository.findByItemID(reservation.getItemID());

        reservation.setStatus(Status.INACTIVE);
        item.setQuantityAvailable(item.getQuantityAvailable() + reservation.getQuantity());

        reservationsRepository.save(reservation);
        itemsRepository.save(item);

        reservationResponseDTO.setResponse(new ResponseEntity<>("DELETED", HttpStatus.OK));

        return reservationResponseDTO;
    }

    public ReservationDTO getReservation(String reservationID) {

        System.out.println("here");
        Reservation reservation = reservationsRepository.findByReservationID(reservationID);
        ReservationDTO reservationDTO = new ReservationDTO();

        if ( reservation == null ) {
            // Reservation with ReservationID not found.
            reservationDTO.setResponse(new ResponseEntity<>("Reservation NOT FOUND", HttpStatus.NOT_FOUND));

        } else if ( reservation.getStatus() == Status.INACTIVE ) {
            // Reservation with ReservationID DELETED.
            reservationDTO.setResponse(new ResponseEntity<>("Reservation DELETED", HttpStatus.GONE));
            return reservationDTO;

        }

        reservationDTO = ReservationDTO.builder()
                .reservationID(reservation.getReservationID())
                .itemID(reservation.getItemID())
                .quantity(reservation.getQuantity())
                .cost(reservation.getCost())
                .build();

        reservationDTO.setResponse(new ResponseEntity<>("RESERVATION FOUND", HttpStatus.OK ));

        return reservationDTO;

    }

}
