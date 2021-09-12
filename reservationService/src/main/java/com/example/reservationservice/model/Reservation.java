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


@Data
@Builder
@Document(collection = "reservations")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    private String reservationID;

    private String itemID;

    private int quantity;

    private float cost;

    private Status status;

    @CreatedDate
    private Date createdOn;

    @LastModifiedDate
    private Date updatedOn;

}
