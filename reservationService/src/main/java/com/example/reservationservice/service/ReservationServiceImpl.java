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

import java.util.UUID;

public class ReservationServiceImpl implements ReservationService{

    @Autowired
    ReservationsRepository reservationsRepository;

    @Autowired
    ItemsRepository itemsRepository;

    public CreateReservationDTO createReservation(String itemID, int quantity) {

        Item item = itemsRepository.findByItemID(itemID);
        CreateReservationDTO createReservationDTO = new CreateReservationDTO();

        if ( quantity > item.getQuantityAvailable() ) {
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

        Reservation reservation = reservationsRepository.findByReservationID(reservationID);
        ReservationResponseDTO reservationResponseDTO = new ReservationResponseDTO();

        reservation.setStatus(Status.INACTIVE);
        reservationResponseDTO.setResponse(new ResponseEntity<>("DELETED", HttpStatus.OK));

        return reservationResponseDTO;
    }

    public ReservationDTO getReservation(String reservationID) {

        Reservation reservation = reservationsRepository.findByReservationID(reservationID);

        ReservationDTO reservationDTO = ReservationDTO.builder()
                .reservationID(reservation.getReservationID())
                .itemID(reservation.getItemID())
                .quantity(reservation.getQuantity())
                .cost(reservation.getCost())
                .status(reservation.getStatus())
                .build();

        return reservationDTO;

    }

}
