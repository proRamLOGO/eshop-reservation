package com.example.reservationservice.repository;

import com.example.reservationservice.model.Item;
import com.example.reservationservice.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    Reservation findByReservationID(String reservationID);

}
