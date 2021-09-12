package com.example.shoppingcartservice.repository;

import org.springframework.data.jpa.repository.Query;
import com.example.shoppingcartservice.model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepository extends CrudRepository<Cart, String> {

    Cart findByCartId(String cartID);

}