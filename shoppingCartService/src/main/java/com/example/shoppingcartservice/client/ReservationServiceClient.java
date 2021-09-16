package com.example.shoppingcartservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.example.shoppingcartservice.dto.CreateReservationDTO;
import com.example.shoppingcartservice.dto.ReservationDTO;
import com.example.shoppingcartservice.dto.ReservationResponseDTO;

@FeignClient(name = "reservation", url  = "http://" + "${host}" + ":${port}" + "/reservation")
@Component
public interface ReservationServiceClient {

    @PostMapping("/reservation")
    public CreateReservationDTO createReservation(@RequestParam String itemID, @RequestParam int quantity);

    @PutMapping("/reservation")
    public ReservationResponseDTO updateReservation(@RequestParam String reservationID, @RequestParam int newQuantity);

    @DeleteMapping("/reservation/{reservationID}")
    public ReservationResponseDTO deleteReservation(@PathVariable String reservationID);

    @GetMapping("/reservation/{reservationID}")
    public ReservationDTO getReservation(@PathVariable String reservationID);

}
