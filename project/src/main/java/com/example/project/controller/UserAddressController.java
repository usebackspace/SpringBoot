package com.example.project.controller;

import com.example.project.model.Address;
import com.example.project.model.User;
import com.example.project.model.enums.State;
import com.example.project.repository.AddressRepository;
import com.example.project.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/address")
public class UserAddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public UserAddressController(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Show all addresses for the logged-in user
    @GetMapping
    public String viewAddresses(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        List<Address> addresses = addressRepository.findAllByUser(user);
        model.addAttribute("address", addresses);
        return "address"; // Thymeleaf template for showing addresses
    }

    // Show Add Address form
    @GetMapping("/add")
    public String addAddressForm(Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("states", State.values());
        return "add_address"; // Thymeleaf template for adding address
    }

    // Save new address
    @PostMapping("/add")
    public String saveAddress(@ModelAttribute Address address) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        address.setUser(user);
        addressRepository.save(address);

        return "redirect:/address";
    }

    // Delete address
    @PostMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
        return "redirect:/address";
    }
}
