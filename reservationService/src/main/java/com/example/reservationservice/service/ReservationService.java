package com.example.reservationservice.service;

import com.example.reservationservice.dto.CreateReservationDTO;
import com.example.reservationservice.dto.ReservationDTO;
import org.springframework.http.ResponseEntity;

public interface ReservationService {

    /*
     * Creates a reservation with given quantity for the given item.
     * Params:  itemID - ID of item for which reservation needs to be created
     *          quantity - desired quantity which needs to be reserved
     * Returns: A DTO containing the Reservation ID of created reservation and cost per item
     * */
    CreateReservationDTO createReservation(String itemId, int quantity);

    /*
     * Updates a reservation with given quantity for the given item.
     * Params:  reservationId - ID of reservation which needs to be updated
     *          newQuantity - desired quantity which needs to be reserved after updation
     * Returns: Response on the successful completion of updation
     * */
    ResponseEntity updateReservation(String reservationId, int newQuantity);

    /*
     * Deletes a reservation.
     * Params:  reservationId - ID of reservation which needs to be deleted
     * Returns: Response on the successful completion of deletion
     * */
    ResponseEntity deleteReservation(String reservationId);


    /*
     * Fetches requested reservation.
     * Params:  reservationId - ID of reservation.
     * Returns: DTO with vital information of reservation.
     * */
    ReservationDTO getReservation(String reservationId);

}
