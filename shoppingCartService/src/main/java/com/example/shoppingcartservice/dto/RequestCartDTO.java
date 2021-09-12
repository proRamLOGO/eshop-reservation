package com.example.shoppingcartservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class RequestCartDTO extends CartResponseDTO {

    private List<CartItemDTO> cartItems;
    private float cost;

    @CreationTimestamp
    private Date createdOn;

}
