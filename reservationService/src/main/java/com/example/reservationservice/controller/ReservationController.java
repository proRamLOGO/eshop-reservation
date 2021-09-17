package com.example.reservationservice.controller;


import com.example.reservationservice.ReservationServiceApplication;
import com.example.reservationservice.dto.CreateReservationDTO;
import com.example.reservationservice.dto.ReservationDTO;
import com.example.reservationservice.dto.ReservationResponseDTO;
import com.example.reservationservice.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@EnableFeignClients
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    @PostMapping("/reservation")
    public CreateReservationDTO createReservation(@RequestParam String itemID, @RequestParam int quantity) {

        CreateReservationDTO reservationDTO = reservationService.createReservation(itemID, quantity);
        LOGGER.info("Exiting Create Reservation Service");
        return reservationDTO;

    }

    @PutMapping("/reservation")
    public ReservationResponseDTO updateReservation(@RequestParam String reservationID, @RequestParam int quantity) {

        ReservationResponseDTO reservationResponseDTO = reservationService.updateReservation(reservationID, quantity);
        LOGGER.info("Exiting Update Reservation Service");
        return reservationResponseDTO;

    }

    @DeleteMapping("/reservation/{reservationID}")
    public ReservationResponseDTO deleteReservation(@PathVariable String reservationID) {

        ReservationResponseDTO reservationResponseDTO = reservationService.deleteReservation(reservationID);
        LOGGER.info("Exiting Delete Reservation Service");
        return reservationResponseDTO;

    }

    @GetMapping("/reservation/{reservationID}")
    public ReservationDTO getReservation(@PathVariable String reservationID) {

        ReservationDTO reservationDTO = reservationService.getReservation(reservationID);
        LOGGER.info("Exiting Get Reservation Service");
        return reservationDTO;

    }

}
