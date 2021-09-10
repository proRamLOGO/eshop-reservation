package com.example.shoppingcartservice.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@Builder
public class CartItemDTO {

    private String itemID;
    private int quantity;
    private float cost;

}
