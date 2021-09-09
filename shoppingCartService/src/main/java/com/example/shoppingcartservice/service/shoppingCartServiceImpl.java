package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.dto.CreateCartDTO;
import com.example.shoppingcartservice.dto.RequestCartDTO;
import com.example.shoppingcartservice.dto.StatusResponseDTO;
import com.example.shoppingcartservice.model.Cart;
import com.example.shoppingcartservice.model.Reservation;
import com.example.shoppingcartservice.model.Status;
import com.example.shoppingcartservice.repository.CartRepository;
import com.example.shoppingcartservice.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.reservationservice.dto.CreateReservationDTO;


import java.sql.Timestamp;
import java.util.UUID;

@Service
public class shoppingCartServiceImpl implements shoppingCartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    ReservationsRepository reservationsRepository;

    public CreateCartDTO createCart() {

        // New Cart Parameters
//        String cartID = UUID.randomUUID().toString();
//        float amount = 0;
//        Status status = Status.ACTIVE;


        String currentTime = new Timestamp(System.currentTimeMillis()).toString();

        // New Cart created and saved
        Cart cart = Cart.builder()
                            .cartID(UUID.randomUUID().toString())
                            .amount(0)
                            .status(Status.ACTIVE)
                            .createdOn(currentTime)
                            .updatedOn(currentTime)
                            .build();
        cartRepository.save(cart);

        // Service Response
        CreateCartDTO createCartDTO = new CreateCartDTO();
        createCartDTO.setCartID(cart.getCartID());
        createCartDTO.setRequestStatus(HttpStatus.CREATED);

        return createCartDTO;

    }

    HttpStatus addCartItem(String cartID, String itemID, int quantity) {

        Cart cart = cartRepository.findByCartId(cartID);

        ReservationDTO reservationDTO = reservationServiceClient.createReservation(itemID, quantity);

        if ( reservationDTO.getRequestStatus() == HttpStatus.OK ) { // http 201 created

            Reservation newReservation = Reservation.builder()
                                            .reservationID(reservationDTO.reservationID)
                                            .cartID(cartID)
                                            .itemID(itemID)
                                            .build();
            cart.builder()
                    .amount(cart.getAmount()+reservationDTO.getUpdateInAmount())
                    .updatedOn(new Timestamp(System.currentTimeMillis()).toString());

            cartRepository.save(cart);
            reservationsRepository.save(newReservation);

            return HttpStatus.OK;

        } else if ( createReservationDTO.getRequestStatus() == HttpStatus.NOT_ACCEPTABLE ) {
            // quantity is more than available
        }


    }

    StatusResponseDTO updateCartItem(String cartID, String itemID, int quantity) {

        // check for if quantity = 0 then call deleteItem
        //---------------------------------------

        // fetch reservation id
        Reservation reservation = reservationsRepository.findReservationByCartIDAndItemID(cartID, itemID);
        Cart cart = cartRepository.findByCartId(cartID);
        ResponseDTO reservationDTO = reservationServiceClient.updateReservation(reservation.getReservationID(),quantity);

        if (updateReservationStatus.requestStatus == HttpStatus.OK) {
            // amount update
            cart.builder()
                    .amount(cart.getAmount()+reservationDTO.getUpdateInAmount())
                    .updatedOn(new Timestamp(System.currentTimeMillis()).toString());

        } else if ( updateReservationStatus.requestStatus == HttpStatus.NOT_ACCEPTABLE ) {
            // insufficient
        }

    }

    StatusResponseDTO deleteCartItem(String cartID, String itemID) {

        // fetch reservation id
        Reservation reservation = reservationsRepository.findReservationByCartIDAndItemID(cartID, itemID);

        ResponseDTO responseDTO = reservationServiceClient.deleteReservation(reservation.getReservationID());

        assert(deleteReservationStatus.requestStatus == HttpStatus.OK);

        cart.builder()
                .amount(cart.getAmount()+reservationDTO.getUpdateInAmount())
                .updatedOn(new Timestamp(System.currentTimeMillis()).toString());

        StatusResponseDTO statusResponseDTO = new StatusResponseDTO(HttpStatus.OK);

        // what about sctable 2

    }

    StatusResponseDTO deleteCart(String cartID) {
        Cart cart = cartRepository.findByCartId(cartID);
        cart.setStatus(Status.INACTIVE);
        StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
        statusResponseDTO.setStatus(HttpStatus.OK);
        return statusResponseDTO;
    }

    RequestCartDTO getCart(String cartID);

}
