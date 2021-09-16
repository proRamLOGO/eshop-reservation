package com.example.shoppingcartservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDTO extends com.example.shoppingcartservice.dto.ReservationResponseDTO {

    @Id
    private String reservationID;

    private float costPerItem;

}
