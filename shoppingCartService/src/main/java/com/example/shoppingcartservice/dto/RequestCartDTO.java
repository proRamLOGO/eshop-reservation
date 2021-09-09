package com.example.shoppingcartservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RequestCartDTO {

    public List<CartItemDTO> cartItems;
    public float amount;

}
