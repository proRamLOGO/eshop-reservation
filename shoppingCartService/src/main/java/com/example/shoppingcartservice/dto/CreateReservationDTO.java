package com.example.shoppingcartservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * CreateReservationDTO Class
 * @Author : Shubh Bansal
 *
 * Attributes:
 * - reservationId : String
 * - costPerItem : float
 *
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDTO  {

    private String reservationId;

    private float costPerItem;

}
