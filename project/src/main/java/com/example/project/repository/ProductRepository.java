package com.example.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.Product;
import com.example.project.model.enums.Category;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

}
