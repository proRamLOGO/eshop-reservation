package com.example.reservationservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class CreateReservationDTO extends ReservationResponseDTO {

    @Id
    private String reservationID;

}
