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

            createReservationDTO.setReservationID(reservation.getReservationID());
            createReservationDTO.setCostPerItem(item.getCost());
            createReservationDTO.setResponse(new ResponseEntity<>("CREATED", HttpStatus.CREATED));

        }

        return createReservationDTO;

    }

    public ReservationResponseDTO updateReservation(String reservationID, int quantity);

    public ReservationResponseDTO deleteReservation(String reservationID);

    public ReservationDTO getReservation(String reservationID);

}
