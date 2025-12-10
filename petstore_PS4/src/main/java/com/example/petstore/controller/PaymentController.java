package com.example.petstore.controller;

import com.example.petstore.model.Address;
import com.example.petstore.repository.AddressRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    private final AddressRepository addressRepository;

    public PaymentController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @PostMapping("/payment")
    public String paymentPage(@RequestParam("selectedAddress") Long addressId,
                              @RequestParam("userId") Long userId,
                              Model model) {

        Address address = addressRepository.findById(addressId).orElseThrow();
        model.addAttribute("selectedAddress", address);
        model.addAttribute("userId", userId); // pass userId to payment page

        return "payment";
    }

    
}
