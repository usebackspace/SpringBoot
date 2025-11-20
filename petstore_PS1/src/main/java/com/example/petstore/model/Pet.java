package com.example.petstore.model;

import com.example.petstore.model.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;  // now using enum instead of String

    private String smallDescription;

    @Lob
    private String description;

    private Integer sellingPrice;
    private Integer discountedPrice;

    @Lob
    private byte[] petImage; // store actual image in DB
}

