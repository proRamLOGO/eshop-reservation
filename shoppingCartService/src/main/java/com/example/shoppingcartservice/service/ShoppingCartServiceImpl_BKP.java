package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.dto.CartItemDTO;
import com.example.shoppingcartservice.dto.CartResponseDTO;
import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.RequestCartDTO;
import com.example.shoppingcartservice.model.Cart;
import com.example.shoppingcartservice.model.CartItem;
import com.example.shoppingcartservice.model.Status;
import com.example.shoppingcartservice.repository.CartItemsRepository;
import com.example.shoppingcartservice.repository.CartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ShoppingCartServiceImpl_BKP implements ShoppingCartService {

    @Autowired
    CartsRepository cartsRepository;
    @Autowired
    CartItemsRepository cartItemsRepository;

    public CreateCartDTO createCart() {

        String currentTime = new Timestamp(System.currentTimeMillis()).toString();

        // New Cart created and saved
        Cart cart = Cart.builder()
                            .cartID(UUID.randomUUID().toString())
                            .cost(0)
                            .status(Status.ACTIVE)
                            .createdOn(currentTime)
                            .updatedOn(currentTime)
                            .build();
        cartsRepository.save(cart);

        // Service Response
        CreateCartDTO createCartDTO = new CreateCartDTO();
        createCartDTO.setCartID(cart.getCartID());
        createCartDTO.setResponse(new ResponseEntity<>("CART CREATED", HttpStatus.CREATED));

        return createCartDTO;

    }
//
//    CartResponseDTO addCartItem(String cartID, String itemID, int quantity) {
//
//        Cart cart = cartRepository.findByCartId(cartID);
//        CreateReservationDTO reservationDTO = reservationServiceClient.createReservation(itemID, quantity);
//        CartResponseDTO cartResponseDTO = new CartResponseDTO();
//
//        if ( reservationDTO.getResponse().getStatusCode() == HttpStatus.CREATED ) {
//
//            CartItem cartItem = CartItem.builder()
//                    .cartItemID(UUID.randomUUID().toString())
//                    .cartID(cartID)
//                    .itemID(itemID)
//                    .reservationID(reservationDTO.getReservationID())
//                    .quantity(quantity)
//                    .amount(reservationDTO.amount)
//                    .build();
//
//            float newCost = cartItem.getCostPerItem() * quantity;
//            cart.setCost(cart.getCost()+newCost);
//            cart.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
//
//            cartRepository.save(cart);
//            cartItemsRepository.save(cartItem);
//
//            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM ADDED", HttpStatus.OK));
//
//        } else if ( createReservationDTO.getResonse().getRequestStatus() == HttpStatus.NOT_FOUND ) {
//
//            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM NOT FOUND", HttpStatus.NOT_FOUND));
//
//        } else if ( createReservationDTO.getResonse().getRequestStatus() == HttpStatus.EXPECTATION_FAILED ) {
//
//            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM QUANTITY NOT AVAILABLE", HttpStatus.EXPECTATION_FAILED));
//
//        } else {
//
//            cartResponseDTO.setResponse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//
//        }
//
//        return cartResponseDTO;
//
//    }
//
//    CartResponseDTO updateCartItem(String cartID, String itemID, int quantity) {
//
//        // check for if quantity = 0 then call deleteItem
//        //---------------------------------------
//
//        Cart cart = cartRepository.findByCartId(cartID);
//        CartItem cartItem = cartItemsRepository.findByCartIDAndItemID(cartID, itemID);
//        ReservationResponseDTO reservationResponseDTO = reservationServiceClient.updateReservation(cartItem.getReservationID(),quantity);
//        CartResponseDTO cartResponseDTO = new CartResponseDTO();
//
//        if (reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.OK) {
//
//            float originalCost = cartItem.getCostPerItem() * cartItem.getQuantity();
//            float newCost = cartItem.getCostPerItem() * quantity;
//            float cartCostChange = newCost - originalCost;
//            float newCartCost = cart.getCost() + cartCostChange;
//
//            cartItem.setQuantity(quantity);
//            cart.setCost(newCartCost);
//            cart.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
//
//            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM ADDED", HttpStatus.OK));
//
//        } else if ( reservationResponseDTO.getResponse().getRequestStatus() == HttpStatus.NOT_FOUND ) {
//
//            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM NOT FOUND", HttpStatus.NOT_FOUND));
//
//        } else if ( reservationResponseDTO.getResponse().getRequestStatus() == HttpStatus.EXPECTATION_FAILED ) {
//
//            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM QUANTITY NOT AVAILABLE", HttpStatus.EXPECTATION_FAILED));
//
//        } else {
//
//            cartResponseDTO.setResponse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//
//        }
//
//        return cartResponseDTO;
//
//    }
//
//    CartResponseDTO deleteCartItem(String cartID, String itemID) {
//
//        Cart cart = cartRepository.findByCartId(cartID);
//        CartItem cartItem = cartItemsRepository.findByCartIDAndItemID(cartID, itemID);
//        ReservationResponseDTO reservationResponseDTO = reservationServiceClient.deleteReservation(cartItem.getReservationID());
//        CartResponseDTO cartResponseDTO = new CartResponseDTO();
//
//        if (reservationResponseDTO.getResponse().getStatusCode() == HttpStatus.OK) {
//            float totalItemCost = cartItem.getCostPerItem() * cartItem.getQuantity();
//            float newCartCost = cart.getCost() - totalItemCost;
//
//            cartItem.setStatus(Status.INACTIVE);
//            cart.setCost(newCartCost);
//            cart.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
//
//            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM DELETED", HttpStatus.OK));
//
//        } else if ( reservationResponseDTO.getResponse().getRequestStatus() == HttpStatus.NOT_FOUND ) {
//
//            cartResponseDTO.setResponse(new ResponseEntity<>("ITEM RESERVATION NOT FOUND", HttpStatus.NOT_FOUND));
//            // since its present here and not found in reservation, should we update it here ?
//
//        } else {
//
//            cartResponseDTO.setResponse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//
//        }
//
//        return cartResponseDTO;
//
//    }

    public CartResponseDTO deleteCart(String cartID) {
        Cart cart = cartsRepository.findByCartId(cartID);
        cart.setStatus(Status.INACTIVE);
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setResponse(new ResponseEntity<>(HttpStatus.OK));
        return cartResponseDTO;
    }

    public RequestCartDTO getCart(String cartID) {

        RequestCartDTO requestCartDTO = new RequestCartDTO();
        Cart cart = cartsRepository.findByCartId(cartID);

        if ( cart.getStatus() == Status.INACTIVE ) {
            requestCartDTO.setResponse(new ResponseEntity<>("CART INACTIVE", HttpStatus.CONFLICT));

        } else {

            List<CartItem> cartItemList = cartItemsRepository.findByCartID(cartID);
            List<CartItemDTO> cartItemDTOList = new ArrayList<>();

            for ( CartItem cartItem : cartItemList ) {

                float cartItemCost = cartItem.getQuantity()*cartItem.getCostPerItem();

                CartItemDTO cartItemDTO = CartItemDTO.builder()
                        .itemID(cartItem.getItemID())
                        .quantity(cartItem.getQuantity())
                        .cost(cartItemCost)
                        .build();
                cartItemDTOList.add(cartItemDTO);

            }

            requestCartDTO.setCartItems(cartItemDTOList);
            requestCartDTO.setCost(cart.getCost());
            requestCartDTO.setCreatedOn(cart.getCreatedOn());
            requestCartDTO.setUpdatedOn(cart.getUpdatedOn());

        }

        return requestCartDTO;

    }

}
