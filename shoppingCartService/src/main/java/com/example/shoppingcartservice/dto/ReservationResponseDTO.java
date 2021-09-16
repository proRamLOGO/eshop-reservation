package com.example.shoppingcartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {

    private ResponseEntity<String> response;

}