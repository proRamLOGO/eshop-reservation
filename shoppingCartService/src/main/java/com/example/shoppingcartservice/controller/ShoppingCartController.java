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
        /*
         * Uses shopping cart service to create a new blank cart.
         * Params:  None
         * Returns: A DTO containing the ID of newly created cart.
         * */

        CreateCartDTO createCartDTO = shoppingCartService.createCart();
        LOGGER.info("Exiting Create Cart Service.");
        return createCartDTO;

    }

    @PostMapping("/item")
    public CartDTO addCartItem(@RequestParam String cartId, @RequestParam String itemId, @RequestParam int quantity ) {
        /*
         * Uses shopping cart service to add a new item to cart with given quantity. Calls reservation microservice to reserve the given quantity of item. If the request is fulfilled successfully a CartDTO is returned containing information of updated cart.
         * Params:  cartID - ID of cart in which item needs to be added
         *          itemID - ID of item which needs to be added
         *          quantity - desired quantity of item which needs to be added
         * Returns: A DTO containing the details of cart after updation.
         * */

        CartDTO cartDTO = shoppingCartService.addCartItem(cartId,itemId,quantity);
        LOGGER.info("Exiting Add Cart Item Service.");
        return cartDTO;

    }

    @PutMapping("/item")
    public CartDTO updateCartItem(@RequestParam String cartId, @RequestParam String itemId, @RequestParam int quantity ) {
        /*
         * Uses shopping cart service to update quantity of an already existing item in a cart with given new quantity. Calls reservation microservice to update the given quantity of item. If the request is fulfilled successfully a CartDTO is returned containing information of updated cart.
         * Params:  cartID - ID of cart in which item needs to be updated
         *          itemID - ID of item which needs to be updated
         *          quantity - new desired quantity of item
         * Returns: A DTO containing the details of cart after updation.
         * */

        CartDTO cartDTO = shoppingCartService.updateCartItem(cartId,itemId,quantity);
        LOGGER.info("Exiting Update Item Service.");
        return cartDTO;

    }

    @DeleteMapping("/item")
    public CartDTO deleteCartItem(@RequestParam String cartId, @RequestParam String itemId) {
        /*
         * Uses shopping cart service to delete an already existing item from the respective cart. Calls reservation microservice to update the changes. If the request is fulfilled successfully a CartDTO is returned containing information of updated cart.
         * Params:  cartID - ID of cart from which item needs to be deleted
         *          itemID - ID of item which needs to be deleted
         * Returns: A DTO containing the details of cart after updation.
         * */

        CartDTO cartDTO = shoppingCartService.deleteCartItem(cartId,itemId);
        LOGGER.info("Exiting Delete Item Service.");
        return cartDTO;

    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity deleteCart(@PathVariable String cartId) {
        /*
         * Uses shopping cart service to delete an already existing cart. A response is returned about successful completion of this process.
         * Params:  cartID - ID of cart which needs to be deleted
         * Returns: A Response Entity
         * */

        ResponseEntity response = shoppingCartService.deleteCart(cartId);
        LOGGER.info("Exiting Update Cart Service.");
        return response;

    }

    @GetMapping("/cart/{cartId}")
    public CartDTO getCart(@PathVariable String cartId) {
        /*
         * Uses shopping cart service to fetch a cart and items in it and returns all this information in an organised manner.
         * Params:  cartID - ID of cart from which item needs to be fetched.
         * Returns: A CartDTO containing the details of cart.
         * */

        CartDTO cartDTO = shoppingCartService.getCart(cartId);
        LOGGER.info("Exiting Get Cart Service.");
        return cartDTO;

    }
}
