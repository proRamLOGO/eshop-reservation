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


    /*
     * Creates a new blank cart.
     * Params:  None
     * Returns: A DTO containing the ID of newly created cart.
     * */
    CreateCartDTO createCart();


    /*
     * Adds a new unique item to cart with given quantity. Calls reservation microservice to reserve the given quantity of item. If the request is fulfilled successfully a CartDTO is returned containing information of updated cart.
     * Params:  cartID - ID of cart in which item needs to be added
     *          itemID - ID of item which needs to be added
     *          quantity - desired quantity of item which needs to be added
     * Returns: A DTO containing the details of cart after updation.
     * */
    CartDTO addCartItem(String cartId, String itemId, int quantity);


    /*
     * Updates the quantity of an already existing item in a cart with given new quantity. Calls reservation microservice to update the given quantity of item. If the request is fulfilled successfully a CartDTO is returned containing information of updated cart.
     * Params:  cartID - ID of cart in which item needs to be updated
     *          itemID - ID of item which needs to be updated
     *          quantity - new desired quantity of item
     * Returns: A DTO containing the details of cart after updation.
     * */
    CartDTO updateCartItem(String cartId, String itemId, int quantity);



    /*
     * Deletes an already existing item from the respective cart. Calls reservation microservice to update the changes. If the request is fulfilled successfully a CartDTO is returned containing information of updated cart.
     * Params:  cartID - ID of cart from which item needs to be deleted
     *          itemID - ID of item which needs to be deleted
     * Returns: A DTO containing the details of cart after updation.
     * */
    CartDTO deleteCartItem(String cartId, String itemId);


    /*
     * Delete an already existing cart. A response is returned about successful completion of this process.
     * Params:  cartID - ID of cart which needs to be deleted
     * Returns: A Response Entity
     * */
    ResponseEntity deleteCart(String cartId);


    /*
     * Fetches a cart and items in it and returns all this information in an organised manner.
     * Params:  cartID - ID of cart from which item needs to be fetched.
     * Returns: A CartDTO containing the details of cart.
     * */
    CartDTO getCart(String cartId);

}
