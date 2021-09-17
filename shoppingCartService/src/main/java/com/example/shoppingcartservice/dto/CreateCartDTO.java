package com.example.shoppingcartservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.Id;

@Data
@NoArgsConstructor
public class CreateCartDTO extends CartResponseDTO {

    private String cartId;

}
