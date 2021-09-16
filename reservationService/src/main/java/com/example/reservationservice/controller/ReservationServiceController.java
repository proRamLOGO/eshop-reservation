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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@EnableFeignClients
public class ReservationServiceController {

    @Autowired
    ReservationService reservationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    @PostMapping("/reservation")
    public CreateReservationDTO createReservation(@RequestParam String itemID, @RequestParam int quantity) {

        CreateReservationDTO reservationDTO = reservationService.createReservation(itemID, quantity);
        LOGGER.info("Item NOT Found");
        if ( reservationDTO.getResponse().getStatusCode() == HttpStatus.OK ) {
            LOGGER.info("Reservation Created");
        } else if ( reservationDTO.getResponse().getStatusCode() == HttpStatus.EXPECTATION_FAILED ) {
            LOGGER.info("Shortage of Stock");
        } else if ( reservationDTO.getResponse().getStatusCode() == HttpStatus.NOT_FOUND ) {
            LOGGER.info("Item NOT Found");
        }

      return reservationDTO;

    }

    @PutMapping("/reservation")
    public ReservationResponseDTO updateReservation(@RequestParam String reservationID, @RequestParam int quantity) {

        ReservationResponseDTO reservationResponseDTO = reservationService.updateReservation(reservationID, quantity);

        if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.OK ) {
            LOGGER.info("Reservation Updated");
        } else if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.EXPECTATION_FAILED ) {
            LOGGER.info("Shortage of Stock");
        } else if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.NOT_FOUND ) {
            LOGGER.info("Reservation NOT Found");
        } else if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.GONE ) {
            LOGGER.info("Reservation Deleted");
        }

        return reservationResponseDTO;

    }

    @DeleteMapping("/reservation/{reservationID}")
    public ReservationResponseDTO deleteReservation(@PathVariable String reservationID) {

        ReservationResponseDTO reservationResponseDTO = reservationService.deleteReservation(reservationID);

        if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.OK ) {
            LOGGER.info("Reservation Deleted");
        } else if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.NOT_FOUND ) {
            LOGGER.info("Reservation NOT Found");
        } else if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.GONE ) {
            LOGGER.info("Reservation Deleted Already");
        }

        return reservationResponseDTO;

    }

    @GetMapping("/reservation/{reservationID}")
    public ReservationDTO getReservation(@PathVariable String reservationID) {

        ReservationDTO reservationDTO = reservationService.getReservation(reservationID);

        if ( reservationDTO.getResponse().getStatusCode() == HttpStatus.OK ) {
            LOGGER.info("Reservation Found");
        } else if ( reservationDTO.getResponse().getStatusCode() == HttpStatus.NOT_FOUND ) {
            LOGGER.info("Reservation NOT Found");
        } else if ( reservationDTO.getResponse().getStatusCode() == HttpStatus.GONE ) {
            LOGGER.info("Reservation Deleted");
        }

        return reservationDTO;

    }

}
