package com.example.shoppingcartservice.repository;

import com.example.shoppingcartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/*
 * CartRepository Repository
 * @Author : Shubh Bansal
 *
 * */
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    /*
     * Finds and returns a unique Cart from 'carts' table with given cartId.
     * Params:  cartId - cart requested
     * Returns: Cart Object for corresponding cart.
     * */
    Cart findByCartId(String cartId);

}