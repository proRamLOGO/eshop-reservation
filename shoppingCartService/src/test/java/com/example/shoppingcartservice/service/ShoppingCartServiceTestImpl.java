package com.example.shoppingcartservice.service;

import com.example.shoppingcartservice.client.ReservationServiceClient;
import com.example.shoppingcartservice.repository.CartItemRepository;
import com.example.shoppingcartservice.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShoppingCartServiceTestImpl {

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ReservationServiceClient reservationServiceClient;

//    @Test
//    void createCart() {
//
//    }
//
//    @Test
//    void addItem() {
//
//    }
//
//    @Test
//    void updateItem() {
//
//    }
//
//    @Test
//    void deleteItem() {
//
//    }
//
//    @Test
//    void deleteCart() {
//
//    }


}
