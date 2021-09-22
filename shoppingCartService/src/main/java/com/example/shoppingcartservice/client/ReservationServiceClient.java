package com.example.shoppingcartservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.example.shoppingcartservice.dto.CreateReservationDTO;
import com.example.shoppingcartservice.dto.ReservationDTO;

/*
 * ReservationServiceClient Feign Client Interface
 * @Author : Shubh Bansal
 *
 * Methods:
 * - createReservation : CreateReservationDTO
 * - updateReservation : ResponseEntity
 * - deleteReservation : ResponseEntity
 * - getReservation : ReservationDTO
 *
 * */

@FeignClient(name = "reservation", url  = "http://localhost:8080/reservation")
@Component
public interface ReservationServiceClient {

    @PostMapping("/reservation")
    CreateReservationDTO createReservation(@RequestParam String itemId, @RequestParam int quantity);

    @PutMapping("/reservation")
    ResponseEntity updateReservation(@RequestParam String reservationId, @RequestParam int newQuantity);

    @DeleteMapping("/reservation/{reservationId}")
    ResponseEntity deleteReservation(@PathVariable String reservationId);

    @GetMapping("/reservation/{reservationId}")
    ReservationDTO getReservation(@PathVariable String reservationId);

}
