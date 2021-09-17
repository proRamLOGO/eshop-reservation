package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.client.ReservationServiceClient;
import com.example.shoppingcartservice.dto.*;
import com.example.shoppingcartservice.model.Cart;
import com.example.shoppingcartservice.model.CartItem;
import com.example.shoppingcartservice.model.Status;
import com.example.shoppingcartservice.repository.CartRepository;
import com.example.shoppingcartservice.repository.CartItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ReservationServiceClient reservationServiceClient;

    public CreateCartDTO createCart() {

        // New Cart created and saved
        Cart cart = Cart.builder()
                            .cartId(UUID.randomUUID().toString())
                            .cost(0)
                            .status(Status.ACTIVE)
                            .build();
        cartRepository.save(cart);

        // Service Response
        CreateCartDTO createCartDTO = new CreateCartDTO();
        createCartDTO.setCartId(cart.getCartId());
        createCartDTO.setResponse(new ResponseEntity<>("CART CREATED", HttpStatus.CREATED));

        return createCartDTO;

    }

    public CartResponseDTO addCartItem(String cartId, String itemId, int quantity) {

        Cart cart = cartRepository.findByCartId(cartId);
        CreateReservationDTO createReservationDTO = reservationServiceClient.createReservation(itemId, quantity);
        CartResponseDTO cartResponseDTO = new CartResponseDTO();

        if ( createReservationDTO.getResponse().getStatusCode() == HttpStatus.CREATED ) {

            CartItem cartItem = CartItem.builder()
                    .cartItemId(UUID.randomUUID().toString())
                    .cartId(cartId)
                    .itemId(itemId)
                    .reservationId(createReservationDTO.getReservationId())
                    .quantity(quantity)
                    .costPerItem(createReservationDTO.getCostPerItem())
                    .build();

            float newCost = cartItem.getCostPerItem() * quantity;
            cart.setCost(cart.getCost()+newCost);

            cartRepository.save(cart);
            cartItemRepository.save(cartItem);

            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM ADDED", HttpStatus.OK));

        } else if (createReservationDTO.getResponse().getStatusCode() == HttpStatus.NOT_FOUND ) {

            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM NOT FOUND", HttpStatus.NOT_FOUND));

        } else if ( createReservationDTO.getResponse().getStatusCode() == HttpStatus.EXPECTATION_FAILED ) {

            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM QUANTITY NOT AVAILABLE", HttpStatus.EXPECTATION_FAILED));

        } else {

            cartResponseDTO.setResponse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        }

        return cartResponseDTO;

    }

    public CartResponseDTO updateCartItem(String cartId, String itemId, int newQuantity) {

        // check for if quantity = 0 then call deleteItem
        //---------------------------------------

        Cart cart = cartRepository.findByCartId(cartId);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cartId, itemId);
        ReservationResponseDTO reservationResponseDTO = reservationServiceClient.updateReservation(cartItem.getReservationId(),newQuantity);
        CartResponseDTO cartResponseDTO = new CartResponseDTO();

        if (reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.OK) {

            float originalCost = cartItem.getCostPerItem() * cartItem.getQuantity();
            float newCost = cartItem.getCostPerItem() * newQuantity;
            float cartCostChange = newCost - originalCost;
            float newCartCost = cart.getCost() + cartCostChange;

            cartItem.setQuantity(newQuantity);
            cart.setCost(newCartCost);

            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM ADDED", HttpStatus.OK));

        } else if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.NOT_FOUND ) {

            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM NOT FOUND", HttpStatus.NOT_FOUND));

        } else if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.EXPECTATION_FAILED ) {

            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM QUANTITY NOT AVAILABLE", HttpStatus.EXPECTATION_FAILED));

        } else {

            cartResponseDTO.setResponse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        }

        return cartResponseDTO;

    }

    public CartResponseDTO deleteCartItem(String cartId, String itemId) {

        Cart cart = cartRepository.findByCartId(cartId);
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cartId, itemId);
        ReservationResponseDTO reservationResponseDTO = reservationServiceClient.deleteReservation(cartItem.getReservationId());
        CartResponseDTO cartResponseDTO = new CartResponseDTO();

        if (reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.OK) {
            float totalItemCost = cartItem.getCostPerItem() * cartItem.getQuantity();
            float newCartCost = cart.getCost() - totalItemCost;

            cartItem.setStatus(Status.INACTIVE);
            cart.setCost(newCartCost);

            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM DELETED", HttpStatus.OK));

        } else if ( reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.NOT_FOUND ) {

            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM RESERVATION NOT FOUND", HttpStatus.NOT_FOUND));
            // since its present here and not found in reservation, should we update it here ?

        } else {

            cartResponseDTO.setResponse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        }

        return cartResponseDTO;

    }

    public CartResponseDTO deleteCart(String cartId) {

        Cart cart = cartRepository.findByCartId(cartId);
        CartResponseDTO cartResponseDTO = new CartResponseDTO();

        cart.setStatus(Status.INACTIVE);

        cartResponseDTO.setResponse(new ResponseEntity<>(HttpStatus.OK));

        return cartResponseDTO;

    }

    public RequestCartDTO getCart(String cartId) {

        RequestCartDTO requestCartDTO = new RequestCartDTO();
        Cart cart = cartRepository.findByCartId(cartId);

        if ( cart.getStatus() == Status.INACTIVE ) {
            requestCartDTO.setResponse(new ResponseEntity<>("CART INACTIVE", HttpStatus.CONFLICT));

        } else {

            List<CartItem> cartItemList = cartItemRepository.findByCartId(cartId);
            List<CartItemDTO> cartItemDTOList = new ArrayList<>();

            for ( CartItem cartItem : cartItemList ) {

                if ( cartItem.getStatus() == Status.ACTIVE ) {

                    float cartItemCost = cartItem.getQuantity() * cartItem.getCostPerItem();

                    CartItemDTO cartItemDTO = CartItemDTO.builder()
                            .itemId(cartItem.getItemId())
                            .quantity(cartItem.getQuantity())
                            .cost(cartItemCost)
                            .build();
                    cartItemDTOList.add(cartItemDTO);
                }

            }

            requestCartDTO.setCartItems(cartItemDTOList);
            requestCartDTO.setCost(cart.getCost());
            requestCartDTO.setResponse(new ResponseEntity<>("CART FOUND", HttpStatus.OK));

        }

        return requestCartDTO;

    }

}
