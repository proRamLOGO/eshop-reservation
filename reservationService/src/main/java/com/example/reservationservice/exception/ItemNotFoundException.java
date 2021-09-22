package com.example.reservationservice.exception;

import com.example.reservationservice.ReservationServiceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemNotFoundException extends RuntimeException {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceApplication.class);

    public ItemNotFoundException() {
        LOGGER.error("Item not found");
    }

}
