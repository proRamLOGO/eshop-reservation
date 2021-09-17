package com.example.shoppingcartservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * Reservation Class
 * @Author : Shubh Bansal
 * Date : Sept 4, 2021
 *
 * Attributes:
 * - reservationID : Primary Key
 * - cartID : Cart to which this reservation belongs to.
 *
 * */

@Entity
@Data
@Builder
@Table(name = "cart_items")
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @Column(name = "cart_item_id")
    private String cartItemId;

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "reservation_id")
    private String reservationId;

    @Column(name = "item_id")
    private String itemId;

    private int quantity;

    @Column(name = "cost_per_item")
    private float costPerItem;

    @Enumerated(EnumType.STRING)
    private Status status;

}