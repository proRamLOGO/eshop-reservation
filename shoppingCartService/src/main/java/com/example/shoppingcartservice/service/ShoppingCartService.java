package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.RequestCartDTO;
import com.example.shoppingcartservice.dto.CartResponseDTO;

public interface ShoppingCartService {

    public CreateCartDTO createCart();

//    public CartResponseDTO addCartItem(String cartID, String itemID, int quantity);
//
//    public CartResponseDTO updateCartItem(String cartID, String itemID, int quantity);
//
//    public CartResponseDTO deleteCartItem(String cartID, String itemID);

    public CartResponseDTO deleteCart(String cartID);

    public RequestCartDTO getCart(String cartID);

}
