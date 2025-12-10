package com.example.project.controller;

import com.example.project.model.Address;
import com.example.project.repository.AddressRepository;
import com.example.project.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/addresses")
public class AddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressController(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String viewAddresses(Model model) {
        model.addAttribute("addresses", addressRepository.findAll()); 
        return "admin/address/view_address";  
    }

    @GetMapping("/add")
    public String addAddressForm(Model model) {
        model.addAttribute("address", new Address());  
        model.addAttribute("users", userRepository.findAll());  
        return "admin/address/add_address";  
    }

    @PostMapping("/add")
    public String addAddress(@ModelAttribute Address address) {
        addressRepository.save(address);  
        return "redirect:/admin/addresses";  
    }

    @GetMapping("/update/{id}")
    public String updateAddressForm(@PathVariable Long id, Model model) {
        Address address = addressRepository.findById(id).orElse(new Address());
        
        model.addAttribute("address", address);
        model.addAttribute("users", userRepository.findAll());  
        return "admin/address/update_address";  
    }

    @PostMapping("/update")
    public String updateAddress(@ModelAttribute Address address) {
        addressRepository.save(address);  
        return "redirect:/admin/addresses";  
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);  
        return "redirect:/admin/addresses";  
    }
}
