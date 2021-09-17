package com.example.reservationservice.service;

import com.example.reservationservice.dto.CreateReservationDTO;
import com.example.reservationservice.dto.ReservationDTO;
import com.example.reservationservice.dto.ReservationResponseDTO;

public interface ReservationService {

    CreateReservationDTO createReservation(String itemID, int quantity);

    ReservationResponseDTO updateReservation(String reservationID, int newQuantity);

    ReservationResponseDTO deleteReservation(String reservationID);

    ReservationDTO getReservation(String reservationID);

}
