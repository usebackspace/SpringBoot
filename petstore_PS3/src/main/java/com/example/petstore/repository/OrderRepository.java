package com.example.petstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
