package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.RequestCartDTO;
import com.example.shoppingcartservice.dto.CartResponseDTO;

public interface ShoppingCartService {

    public CreateCartDTO createCart();

    public CartResponseDTO addCartItem(String cartId, String itemId, int quantity);

    public CartResponseDTO updateCartItem(String cartId, String itemId, int quantity);

    public CartResponseDTO deleteCartItem(String cartId, String itemId);

    public CartResponseDTO deleteCart(String cartId);

    public RequestCartDTO getCart(String cartId);

}
