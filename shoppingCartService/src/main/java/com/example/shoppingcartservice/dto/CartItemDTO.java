package com.example.shoppingcartservice.dto;

import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class CartItemDTO {

    public String name;
    public int quantity;
    public float amount;

}
