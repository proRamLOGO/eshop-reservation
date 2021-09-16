package com.example.shoppingcartservice.repository;

import com.example.shoppingcartservice.model.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, String> {

    CartItem findByCartIDAndItemID(String cartID, String itemID);
    List<CartItem> findByCartID(String cartID);

}