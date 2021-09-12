package com.example.reservationservice.dto;


import com.example.reservationservice.model.Status;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@Builder
public class ReservationDTO extends ReservationResponseDTO {

    @Id
    private String reservationID;
    // ----------------------- ??? can extend create reservationDTO ??? -----------------------

    private String itemID;

    private int quantity;

    private float cost;

    private Status status;

}
