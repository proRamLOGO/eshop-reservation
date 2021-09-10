package com.example.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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

    String createdOn;

    String updatedOn;

}
