package com.example.petstore.repository;


import com.example.petstore.model.Cart;
import com.example.petstore.model.Pet;
import com.example.petstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Optional<Cart> findByUserAndPet(User user, Pet pet);
}

