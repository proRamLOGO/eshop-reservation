package com.example.shoppingcartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {

    private ResponseEntity<String> response;

}
