package com.example.shoppingcartservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * ReservationDTO Class
 * @Author : Shubh Bansal
 *
 * Attributes:
 * - reservationId : String
 * - itemId : String
 * - quantity : int
 * - cost : float
 *
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {

    private String reservationId;

    private String itemId;

    private int quantity;

    private float cost;

}
