package com.example.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    private String itemID;

    private int quantityAvailable;

    private float cost;

    @LastModifiedDate
    private Date updatedOn;

}
