package com.example.petstore.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petstore.model.Address;
import com.example.petstore.model.User;


public interface AddressRepository extends JpaRepository<Address, Long> {
     List<Address> findAllByUser(User user);
}
