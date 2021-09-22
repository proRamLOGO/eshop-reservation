package com.example.shoppingcartservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/*
* Cart Class
* @Author : Shubh Bansal
*
* Attributes:
* - cartId : String (Primary Key)
* - cost : float
* - status : enum(ACTIVE / INACTIVE)
* - createdOn : Date
* - updatedOn : Date
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
    @Column(name = "cart_id")
    String cartId;

    float cost;

    @Enumerated(EnumType.STRING)
    Status status;

    @CreationTimestamp
    @Column(name = "created_on")
    Date createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    Date updatedOn;

}
