package com.example.shoppingcartservice.repository;

import com.example.shoppingcartservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/*
 * CartItemRepository Repository
 * @Author : Shubh Bansal
 * Database: MySQL
 *
 * */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

    /*
     * Finds and returns a CartItem object corresponding to the item with itemId and belonging to a specific cart with given cartId.
     * Params:  cartId - cart in which item has been added
     *          itemId - id of item requested
     * Returns: CartItem Object for corresponding item
     * */
    CartItem findByCartIdAndItemId(String cartId, String itemId);

    /*
     * Finds and returns a list of all items as CartItem which belongs to a cart with given cartId.
     * Params:  cartId - cart requested
     * Returns: List<CartItem>
     * */
    List<CartItem> findByCartId(String cartId);

}