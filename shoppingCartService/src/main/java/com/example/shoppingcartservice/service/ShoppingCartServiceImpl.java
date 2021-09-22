package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.ShoppingCartServiceApplication;
import com.example.shoppingcartservice.client.ReservationServiceClient;
import com.example.shoppingcartservice.dto.*;
import com.example.shoppingcartservice.model.Cart;
import com.example.shoppingcartservice.model.CartItem;
import com.example.shoppingcartservice.model.Status;
import com.example.shoppingcartservice.repository.CartRepository;
import com.example.shoppingcartservice.repository.CartItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * ShoppingCartService Class
 * @Author : Shubh Bansal
 *
 * Attributes:
 * - LOGGER
 * @Autowired
 *      - cartRepository : CartRepository
 *      - cartItemRepository : CartItemRepository
 *      - reservationServiceClient : ReservationServiceClient
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
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ReservationServiceClient reservationServiceClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartServiceApplication.class);

    public CreateCartDTO createCart() {
        /*
        * Method creates a new cart with a unique cartID and saves it to the database.
        *
        * @params - None
        *
        * @return - CreateCartDTO
        *
        * */


        Cart cart = Cart.builder()
                            .cartId(UUID.randomUUID().toString())
                            .cost(0)
                            .status(Status.ACTIVE)
                            .build();

        // Database Update
        cartRepository.save(cart);

        CreateCartDTO createCartDTO = new CreateCartDTO();
        createCartDTO.setCartId(cart.getCartId());

        LOGGER.info("CART CREATED");

        return createCartDTO;

    }

    public CartDTO addCartItem(String cartId, String itemId, int quantity) {
        /*
         * Method adds a new item with requested quantity to the given cart.
         *
         * @params
         * - cartID : String
         * - itemId : String
         * - quantity : int
         *
         * @return - CartDTO
         *
         * */

        Cart cart = cartRepository.findByCartId(cartId);
        CartDTO cartDTO;

        // False cartID provided
        if ( cart==null ) {
            cartDTO = null;
            return cartDTO;
        }

        CreateReservationDTO createReservationDTO = reservationServiceClient.createReservation(itemId, quantity);

        // Reservation Successful
        if ( createReservationDTO != null ) {

            LOGGER.info("ITEM RESERVED");

            CartItem cartItem = CartItem.builder()
                    .cartItemId(UUID.randomUUID().toString())
                    .cartId(cartId)
                    .itemId(itemId)
                    .reservationId(createReservationDTO.getReservationId())
                    .quantity(quantity)
                    .costPerItem(createReservationDTO.getCostPerItem())
                    .status(Status.ACTIVE)
                    .build();

            float newCost = cartItem.getCostPerItem() * quantity;
            cart.setCost(cart.getCost()+newCost);

            // Database Update
            cartRepository.save(cart);
            cartItemRepository.save(cartItem);

            LOGGER.info("ITEM ADDED");

            cartDTO = getCart(cartId);

        }

        // Reservation Unsuccessful
        else {

            LOGGER.info("BAD REQUEST");
            cartDTO = null;

        }

        return cartDTO;

    }

    public CartDTO updateCartItem(String cartId, String itemId, int newQuantity) {
        /*
         * Method adds updates an already existing cart item with new requested quantity.
         *
         * @params
         * - cartID : String
         * - itemId : String
         * - newQuantity : int
         *
         * @return - CartDTO
         *
         * */

        Cart cart = cartRepository.findByCartId(cartId);

        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cartId, itemId);
        ResponseEntity reservationResponseDTO = reservationServiceClient.updateReservation(cartItem.getReservationId(),newQuantity);
        CartDTO cartDTO;

        // Reservation Update Successful
        if (reservationResponseDTO != null) {

            float originalCost = cartItem.getCostPerItem() * cartItem.getQuantity();
            float newCost = cartItem.getCostPerItem() * newQuantity;
            float cartCostChange = newCost - originalCost;
            float newCartCost = cart.getCost() + cartCostChange;

            cartItem.setQuantity(newQuantity);
            cart.setCost(newCartCost);

            // Database Update
            cartRepository.save(cart);
            cartItemRepository.save(cartItem);

            cartDTO = getCart(cartId);

            LOGGER.info("ITEM UPDATED");

        }

        // Reservation Update Unsuccessful
        else {

            cartDTO = null;
            LOGGER.info("ITEM NOT UPDATED");

        }

        return cartDTO;

    }

    public CartDTO deleteCartItem(String cartId, String itemId) {
        /*
         * Method deletes an already existing cart item.
         *
         * @params
         * - cartID : String
         * - itemId : String
         *
         * @return - CartDTO
         *
         * */

        Cart cart = cartRepository.findByCartId(cartId);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cartId, itemId);
        ResponseEntity reservationResponseDTO = reservationServiceClient.deleteReservation(cartItem.getReservationId());
        CartDTO cartDTO;

        // Reservation Delete Successful
        if (reservationResponseDTO != null) {

            float totalItemCost = cartItem.getCostPerItem() * cartItem.getQuantity();
            float newCartCost = cart.getCost() - totalItemCost;

            cartItem.setStatus(Status.INACTIVE);
            cart.setCost(newCartCost);

            // Database Update
            cartRepository.save(cart);
            cartItemRepository.save(cartItem);

            cartDTO = getCart(cartId);

        }

        // Reservation Delete Unsuccessful
        else {

            cartDTO = null;

        }

        return cartDTO;

    }

    public ResponseEntity deleteCart(String cartId) {
        /*
         * Method deletes the cart completely.
         *
         * @params - cartID : String
         *
         * @return - ResponseEntity
         *
         * */

        Cart cart = cartRepository.findByCartId(cartId);
        ResponseEntity response;

        // False CartID provided or cart already deleted.
        if ( cart==null || cart.getStatus()==Status.INACTIVE ) {
            response = new ResponseEntity("CART NOT FOUND", HttpStatus.NOT_FOUND);
        }

        // Cart deleted successfully
        else {
            cart.setStatus(Status.INACTIVE);

            // Database Update
            cartRepository.save(cart);

            response = new ResponseEntity("CART DELETED", HttpStatus.OK);

        }
        return response;

    }

    public CartDTO getCart(String cartId) {
        /*
         * Method fetches the cart with given cartID and returns a DTO with all vital information including a list of ACTIVE items in the cart.
         *
         * @params - cartID : String
         *
         * @return - CartDTO
         *
         * */

        CartDTO cartDTO = new CartDTO();
        Cart cart = cartRepository.findByCartId(cartId);

        // Cart Deleted
        if ( cart.getStatus() == Status.INACTIVE ) {
            cartDTO = null;
        }

        // Cart Found
        else {

            List<CartItem> cartItemList = cartItemRepository.findByCartId(cartId);
            List<CartItemDTO> cartItemDTOList = new ArrayList<>();

            for ( CartItem cartItem : cartItemList ) {

                // Include cartItem iff it is NOT DELETED i.e. it's status is set to ACTIVE
                if ( cartItem.getStatus() == Status.ACTIVE ) {

                    float cartItemCost = cartItem.getQuantity() * cartItem.getCostPerItem();

                    CartItemDTO cartItemDTO = CartItemDTO.builder()
                            .itemId(cartItem.getItemId())
                            .quantity(cartItem.getQuantity())
                            .cost(cartItemCost)
                            .reservationId(cartItem.getReservationId())
                            .build();
                    cartItemDTOList.add(cartItemDTO);
                }

            }

            cartDTO.setCartItems(cartItemDTOList);
            cartDTO.setCost(cart.getCost());

        }

        return cartDTO;

    }

}
