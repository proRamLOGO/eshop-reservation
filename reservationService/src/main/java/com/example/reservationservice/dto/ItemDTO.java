package com.example.reservationservice.dto;

import com.example.reservationservice.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO {

    private String itemID;

    private int quantityAvailable;

    private float cost;

    private Date updatedOn;

    private ResponseEntity<String> response;

}
