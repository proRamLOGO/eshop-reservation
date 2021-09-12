package com.example.shoppingcartservice.controller;


import com.example.shoppingcartservice.dto.CartResponseDTO;
import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.RequestCartDTO;
import com.example.shoppingcartservice.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartController.class);

    @PostMapping("/cart")
    public CreateCartDTO createCart() {

        CreateCartDTO createCartDTO = shoppingCartService.createCart();
        LOGGER.info("New Cart "+createCartDTO.getCartID()+" Created");
        return createCartDTO;

    }
//
//    @PostMapping("/item")
//    public CartResponseDTO addItem(@RequestParam String cartId, @RequestParam String itemId, @RequestParam int quantity ) {
//
//        CartResponseDTO cartResponseDTO = shoppingCartService.addCartItem(cartId,itemId,quantity);
//        LOGGER.info("Item Added");
//        return cartResponseDTO;
//
//    }
//
//    @PutMapping("/item")
//    public CartResponseDTO updateItem(@RequestParam String cartId, @RequestParam String itemId, @RequestParam int quantity ) {
//
//        CartResponseDTO cartResponseDTO = shoppingCartService.updateCartItem(cartId,itemId,quantity);
//        LOGGER.info("Item Updated");
//        return cartResponseDTO;
//
//    }
//
//    @DeleteMapping("/item")
//    public CartResponseDTO deleteItem(@RequestParam String cartId, @RequestParam String itemId) {
//
//        CartResponseDTO cartResponseDTO = shoppingCartService.deleteCartItem(cartId,itemId);
//        LOGGER.info("Item Deleted");
//        return cartResponseDTO;
//
//    }

    @DeleteMapping("/cart/{cartID}")
    public CartResponseDTO deleteCart(@PathVariable String cartId) {

        CartResponseDTO cartResponseDTO = shoppingCartService.deleteCart(cartId);
        LOGGER.info("Cart Deleted");
        return cartResponseDTO;

    }

    @GetMapping("/cart/{cartID}")
    public RequestCartDTO getCart(@PathVariable String cartID) {
        RequestCartDTO requestCartDTO = shoppingCartService.getCart(cartID);
        LOGGER.info("Cart Found and Fetched");
        return requestCartDTO;
    }
}
