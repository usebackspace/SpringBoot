package com.example.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CRUD.model.Avenger;

public interface AvengerRepository extends JpaRepository<Avenger, Long>{
    
}
