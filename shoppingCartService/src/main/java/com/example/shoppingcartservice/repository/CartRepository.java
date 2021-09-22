package com.example.shoppingcartservice.repository;

import com.example.shoppingcartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/*
 * CartRepository Repository
 * @Author : Shubh Bansal
 *
 * Methods:
 * - findByCartId : Cart
 *
 * */
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Cart findByCartId(String cartId);

}