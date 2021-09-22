package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.CartDTO;
import org.springframework.http.ResponseEntity;


/*
 * ShoppingCartService Interface
 * @Author : Shubh Bansal
 *
 * Methods:
 * - createCart : CreateCartDTO
 * - addCartItem : CartDTO
 * - updateCartItem : CartDTO
 * - deleteCartItem : CartDTO
 * - deleteCart : ResponseEntity
 * - getCart : CartDTO
 *
 * */
public interface ShoppingCartService {

    public CreateCartDTO createCart();

    public CartDTO addCartItem(String cartId, String itemId, int quantity);

    public CartDTO updateCartItem(String cartId, String itemId, int quantity);

    public CartDTO deleteCartItem(String cartId, String itemId);

    public ResponseEntity deleteCart(String cartId);

    public CartDTO getCart(String cartId);

}
