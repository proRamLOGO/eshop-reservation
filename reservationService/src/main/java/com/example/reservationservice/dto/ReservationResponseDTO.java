package com.example.reservationservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class ReservationResponseDTO {

    private ResponseEntity<String> response;

}