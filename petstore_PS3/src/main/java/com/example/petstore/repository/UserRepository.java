package com.example.petstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.model.User;


public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
}
