package com.example.shoppingcartservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

/*
 * CartDTO Class
 * @Author : Shubh Bansal
 *
 * */
@Data
@NoArgsConstructor
public class CartDTO  {

    private List<CartItemDTO> cartItems;
    private float cost;

    @CreationTimestamp
    private Date createdOn;

}
