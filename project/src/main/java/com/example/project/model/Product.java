package com.example.project.model;

import com.example.project.model.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {

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
    private byte[] productImage; // store actual image in DB
}

