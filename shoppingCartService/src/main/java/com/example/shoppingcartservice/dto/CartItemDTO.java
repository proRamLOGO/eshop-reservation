package com.example.shoppingcartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

/*
 * CartItemDTO Class
 * @Author : Shubh Bansal
 *
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private String itemId;

    private String reservationId;

    private int quantity;

    private float cost;

}
