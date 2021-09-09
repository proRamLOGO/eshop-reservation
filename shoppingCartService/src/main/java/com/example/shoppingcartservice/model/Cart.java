package com.example.shoppingcartservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/*
* Cart Class
* @Author : Shubh Bansal
* Date : Sept 4, 2021
*
* Attributes:
* - cartID : Primary Key
* - amount : Cost
* - status : enum(ACTIVE / INACTIVE)
* - createdOn : Timestamp for cart creation
* - updatedOn : Timestamp for latest cart updation
*
* */

@Entity
@Data
@Table(name = "shopping_cart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    String cartID;

    float amount;

    @Enumerated(EnumType.STRING)
    Status status;

    @CreationTimestamp
    String createdOn;

    @UpdateTimestamp
    String updatedOn;

}
