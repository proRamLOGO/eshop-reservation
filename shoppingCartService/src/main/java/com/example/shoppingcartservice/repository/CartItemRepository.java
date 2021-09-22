package com.example.shoppingcartservice.repository;

import com.example.shoppingcartservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
 * CartItemRepository Repository
 * @Author : Shubh Bansal
 *
 * Methods:
 * - findByCartIdAndItemId : CartItem
 * - findByCartId : List<CartItem>
 *
 * */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

    CartItem findByCartIdAndItemId(String cartId, String itemId);
    List<CartItem> findByCartId(String cartId);

}