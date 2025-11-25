package com.example.petstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.model.User;

public interface UserRepository extends JpaRepository<User,Long>{
    
}
