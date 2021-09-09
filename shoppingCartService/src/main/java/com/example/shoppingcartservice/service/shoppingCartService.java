package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.RequestCartDTO;
import com.example.shoppingcartservice.dto.StatusResponseDTO;
import org.springframework.http.HttpStatus;

public interface shoppingCartService {

    CreateCartDTO createCart();

    HttpStatus addCartItem(String cartID, String itemID, int quantity);

    StatusResponseDTO updateCartItem(String cartID, String itemID, int quantity);

    StatusResponseDTO deleteCartItem(String cartID, String itemID);

    StatusResponseDTO deleteCart(String cartID);

    RequestCartDTO getCart(String cartID);

}
