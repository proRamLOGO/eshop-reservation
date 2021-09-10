package com.example.shoppingcartservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RequestCartDTO extends CartResponseDTO {

    private List<CartItemDTO> cartItems;
    private float cost;
    private String createdOn;
    private String updatedOn;

}
