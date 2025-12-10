package com.example.project.model;


import com.example.project.model.enums.State;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String area;
    private String city;
    
    @Enumerated(EnumType.STRING)
    private State state;

    private Integer pincode;

    @ManyToOne   //Many addresses can belong to one user.
    @JoinColumn(name = "user_id")   //user_id column will be created in address table as a foreign key to User.id
    private User user;  //Is a reference to the User object in Java.
}
