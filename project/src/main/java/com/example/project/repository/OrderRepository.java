package com.example.project.repository;

import com.example.project.model.Order;
import com.example.project.model.User;
import com.example.project.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Fetch all orders for a user, sorted by most recent first
    List<Order> findByUserOrderByOrderAtDesc(User user);

    // Optional: Fetch all orders for a user filtered by status
    List<Order> findByUserAndStatus(User user, OrderStatus status);
}
