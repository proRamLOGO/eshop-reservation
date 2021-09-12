package com.example.reservationservice.service;

import com.example.reservationservice.dto.CreateReservationDTO;
import com.example.reservationservice.dto.ReservationDTO;
import com.example.reservationservice.dto.ReservationResponseDTO;

public interface ReservationService {

    public CreateReservationDTO createReservation(String itemID, int quantity);

    public ReservationResponseDTO updateReservation(String reservationID, int quantity);

    public ReservationResponseDTO deleteReservation(String reservationID);

    public ReservationDTO getReservation(String reservationID);

}
