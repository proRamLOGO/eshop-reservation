package com.example.reservationservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDTO extends ReservationResponseDTO {

    @Id
    private String reservationID;

    private float costPerItem;

}
