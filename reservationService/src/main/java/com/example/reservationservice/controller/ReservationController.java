package com.example.reservationservice.controller;

import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.dto.CreateReservationDTO;
import com.example.reservationservice.dto.ReservationDTO;
import com.example.reservationservice.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/*
 * ReservationController Class
 * @Author : Shubh Bansal
 *
 * */
@RestController
@RequestMapping("/reservation")
@EnableFeignClients
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    @PostMapping("/reservation")
    public CreateReservationDTO createReservation(@RequestParam String itemId, @RequestParam int quantity) {
        /*
        * Uses reservation service to create a reservation with given quantity for the given item.
        * Params:  itemID - ID of item for which reservation needs to be created
        *          quantity - desired quantity which needs to be reserved
        * Returns: A DTO containing the Reservation ID of created reservation and cost per item
        * */

        CreateReservationDTO reservationDTO = reservationService.createReservation(itemId, quantity);
        LOGGER.info("Exiting Create Reservation Service");
        return reservationDTO;

    }

    @PutMapping("/reservation")
    public ResponseEntity updateReservation(@RequestParam String reservationId, @RequestParam int newQuantity) {
        /*
         * Uses reservation service to update a reservation with given quantity for the given item.
         * Params:  reservationId - ID of reservation which needs to be updated
         *          quantity - desired quantity which needs to be reserved after updation
         * Returns: Response on the successful completion of updation
         * */

        ResponseEntity response = reservationService.updateReservation(reservationId, newQuantity);
        LOGGER.info("Exiting Update Reservation Service");
        return response;

    }

    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity deleteReservation(@PathVariable String reservationId) {
        /*
         * Uses reservation service to delete a reservation.
         * Params:  reservationId - ID of reservation which needs to be deleted
         * Returns: Response on the successful completion of deletion
         * */

        ResponseEntity response = reservationService.deleteReservation(reservationId);
        LOGGER.info("Exiting Delete Reservation Service");
        return response;

    }

    @GetMapping("/reservation/{reservationId}")
    public ReservationDTO getReservation(@PathVariable String reservationId) {
        /*
         * Uses reservation service to get details of a reservation.
         * Params:  reservationId - ID of reservation requested
         * Returns: DTO with relevant details of this reservation
         * */

        ReservationDTO reservationDTO = reservationService.getReservation(reservationId);
        LOGGER.info("Exiting Get Reservation Service");
        return reservationDTO;

    }

}
