package com.example.shoppingcartservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "cart_reservations")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    String reservationID;

    String cartID;

    String itemID;

}
