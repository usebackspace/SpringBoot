package com.example.petstore.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.model.Pet;
import com.example.petstore.model.enums.Category;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByCategory(Category category);

}
