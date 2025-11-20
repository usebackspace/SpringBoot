package com.example.petstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

