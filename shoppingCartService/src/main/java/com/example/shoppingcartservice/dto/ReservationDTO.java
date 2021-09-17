package com.example.shoppingcartservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO extends ReservationResponseDTO {

    private String reservationId;

    private String itemId;

    private int quantity;

    private float cost;

}
