package com.example.petstore.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.model.Cart;
import com.example.petstore.model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
}

