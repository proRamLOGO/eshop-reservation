package com.example.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/*
 * Reservation Class
 * @Author : Shubh Bansal
 *
 * Table Name: reservations
 *
 * Attributes:
 * - reservationId : String (Primary Key)
 * - itemId : String
 * - quantity : int
 * - cost : float
 * - status : enum(ACTIVE / INACTIVE)
 * - createdOn : Date
 * - updatedOn : Date
 *
 * */
@Data
@Builder
@Document(collection = "reservations")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    private String reservationId;

    private String itemId;

    private int quantity;

    private float cost;

    private Status status;

    @CreatedDate
    private Date createdOn;

    @LastModifiedDate
    private Date updatedOn;

}
