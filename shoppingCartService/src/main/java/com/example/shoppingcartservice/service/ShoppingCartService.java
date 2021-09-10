package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.RequestCartDTO;
import com.example.shoppingcartservice.dto.CartResponseDTO;

public interface ShoppingCartService {

    CreateCartDTO createCart();

    CartResponseDTO addCartItem(String cartID, String itemID, int quantity);

    CartResponseDTO updateCartItem(String cartID, String itemID, int quantity);

    CartResponseDTO deleteCartItem(String cartID, String itemID);

    CartResponseDTO deleteCart(String cartID);

    RequestCartDTO getCart(String cartID);

}
