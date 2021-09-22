package com.example.reservationservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO implements Serializable {

    private String reservationId;

    private String itemId;

    private int quantity;

    private float cost;

}
