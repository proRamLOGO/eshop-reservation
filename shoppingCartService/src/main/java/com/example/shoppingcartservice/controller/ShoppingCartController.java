package com.example.shoppingcartservice.controller;


import com.example.shoppingcartservice.ShoppingCartServiceApplication;
import com.example.shoppingcartservice.dto.CartResponseDTO;
import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.RequestCartDTO;
import com.example.shoppingcartservice.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        LOGGER.info("New Cart "+createCartDTO.getCartId()+" Created");
        return createCartDTO;

    }

    @PostMapping("/item")
    public CartResponseDTO addCartItem(@RequestParam String cartId, @RequestParam String itemId, @RequestParam int quantity ) {

        CartResponseDTO cartResponseDTO = shoppingCartService.addCartItem(cartId,itemId,quantity);
        if (cartResponseDTO.getResponse().getStatusCode() == HttpStatus.OK) {
            LOGGER.info("Item Added");
        } else {
            LOGGER.info("Item NOT Added"+cartResponseDTO.getResponse().getBody());
        }
        LOGGER.info("Exiting Add Cart Item Service.");
        return cartResponseDTO;

    }

    @PutMapping("/item")
    public CartResponseDTO updateCartItem(@RequestParam String cartId, @RequestParam String itemId, @RequestParam int quantity ) {

        CartResponseDTO cartResponseDTO = shoppingCartService.updateCartItem(cartId,itemId,quantity);
        LOGGER.info("Item Updated");
        return cartResponseDTO;

    }

    @DeleteMapping("/item")
    public CartResponseDTO deleteCartItem(@RequestParam String cartId, @RequestParam String itemId) {

        CartResponseDTO cartResponseDTO = shoppingCartService.deleteCartItem(cartId,itemId);
        LOGGER.info("Item Deleted");
        return cartResponseDTO;

    }

    @DeleteMapping("/cart/{cartId}")
    public CartResponseDTO deleteCart(@PathVariable String cartId) {

        CartResponseDTO cartResponseDTO = shoppingCartService.deleteCart(cartId);
        LOGGER.info("Cart Deleted");
        return cartResponseDTO;

    }

    @GetMapping("/cart/{cartId}")
    public RequestCartDTO getCart(@PathVariable String cartId) {
        RequestCartDTO requestCartDTO = shoppingCartService.getCart(cartId);
        LOGGER.info("Cart Found and Fetched");
        return requestCartDTO;
    }
}
