package com.example.shoppingcartservice.repository;

import com.example.shoppingcartservice.model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {

    Cart findByCartId(String cartID);

}