package com.example.project.repository;


import com.example.project.model.Cart;
import com.example.project.model.Product;
import com.example.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Optional<Cart> findByUserAndProduct(User user, Product product);
}

