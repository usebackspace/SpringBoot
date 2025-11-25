package com.example.petstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.model.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {
}
