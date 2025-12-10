package com.example.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.Address;
import com.example.project.model.User;


public interface AddressRepository extends JpaRepository<Address, Long> {
     List<Address> findAllByUser(User user);
}
