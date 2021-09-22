package com.example.shoppingcartservice.controller;


import com.example.shoppingcartservice.ShoppingCartServiceApplication;
import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.CartDTO;
import com.example.shoppingcartservice.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/*
 * ShoppingCartController Class
 * @Author : Shubh Bansal
 *
 * Attributes:
 * - LOGGER
 * @Autowired
 *      - shoppingCartService : ShoppingCartService
 *
 * Methods:
 * - POST createCart : CreateCartDTO
 * - POST addCartItem : CartDTO
 * - PUT updateCartItem : CartDTO
 * - DELETE deleteCartItem : CartDTO
 * - DELETE deleteCart : ResponseEntity
 * - GET getCart : CartDTO
 *
 * */

@RefreshScope
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartServiceApplication.class);

    @PostMapping("/cart")
    public CreateCartDTO createCart() {

        CreateCartDTO createCartDTO = shoppingCartService.createCart();
        LOGGER.info("Exiting Create Cart Service.");
        return createCartDTO;

    }

    @PostMapping("/item")
    public CartDTO addCartItem(@RequestParam String cartId, @RequestParam String itemId, @RequestParam int quantity ) {

        CartDTO cartDTO = shoppingCartService.addCartItem(cartId,itemId,quantity);
        LOGGER.info("Exiting Add Cart Item Service.");
        return cartDTO;

    }

    @PutMapping("/item")
    public CartDTO updateCartItem(@RequestParam String cartId, @RequestParam String itemId, @RequestParam int quantity ) {

        CartDTO cartDTO = shoppingCartService.updateCartItem(cartId,itemId,quantity);
        LOGGER.info("Exiting Update Item Service.");
        return cartDTO;

    }

    @DeleteMapping("/item")
    public CartDTO deleteCartItem(@RequestParam String cartId, @RequestParam String itemId) {

        CartDTO cartDTO = shoppingCartService.deleteCartItem(cartId,itemId);
        LOGGER.info("Exiting Delete Item Service.");
        return cartDTO;

    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity deleteCart(@PathVariable String cartId) {

        ResponseEntity response = shoppingCartService.deleteCart(cartId);
        LOGGER.info("Exiting Update Cart Service.");
        return response;

    }

    @GetMapping("/cart/{cartId}")
    public CartDTO getCart(@PathVariable String cartId) {

        CartDTO cartDTO = shoppingCartService.getCart(cartId);
        LOGGER.info("Exiting Get Cart Service.");
        return cartDTO;

    }
}
