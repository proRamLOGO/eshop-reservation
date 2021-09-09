package com.example.shoppingcartservice.repository;

import com.example.shoppingcartservice.model.Cart;
import com.example.shoppingcartservice.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends CrudRepository<Reservation, String> {

    Reservation findByReservationId(String reservationID);
    Reservation findReservationByCartIDAndItemID(String cartID, String itemID);

}