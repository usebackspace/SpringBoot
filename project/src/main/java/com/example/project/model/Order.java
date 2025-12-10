package com.example.project.model;


import com.example.project.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  //Each order belongs to one user, but a user can have many orders.
    @JoinColumn(name = "user_id") ////user_id column in orders table stores the foreign key referencing User.id.
    private User user;

    @ManyToOne  // Each order is for one product, but a product can appear in many orders.
    @JoinColumn(name = "product_id")  ////product_id column stores the foreign key referencing product.id.
    private Product product;

    private Integer quantity;

    private LocalDateTime orderAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // use enum now

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    // Constructor to set defaults
    public Order() {
        this.quantity = 1;
        this.orderAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }
}



