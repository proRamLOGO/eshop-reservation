package com.example.reservationservice.repository;

import com.example.reservationservice.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*
 * ReservationRepository Repository
 * @Author : Shubh Bansal
 * Database: MongoDB
 *
 * */
@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    /*
     * Finds and returns a unique Reservation from 'reservations' table with given reservationId.
     * Params:  reservationId - reservation requested
     * Returns: Reservation Object for corresponding reservation.
     * */
    Reservation findByReservationId(String reservationId);

}
