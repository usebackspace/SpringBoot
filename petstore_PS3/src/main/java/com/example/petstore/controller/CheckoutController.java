package com.example.petstore.controller;

import com.example.petstore.model.Address;
import com.example.petstore.model.Cart;
import com.example.petstore.model.User;
import com.example.petstore.repository.AddressRepository;
import com.example.petstore.repository.CartRepository;
import com.example.petstore.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CheckoutController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public CheckoutController(CartRepository cartRepository, UserRepository userRepository,
                              AddressRepository addressRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        // 1. Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        // 2. Get cart items for user
        List<Cart> cartItems = cartRepository.findByUser(user);

        // 3. Calculate subtotal
        double total = 0;
        for (Cart item : cartItems) {
            total += item.getPet().getDiscountedPrice() * item.getQuantity();
        }

        // 4. Get user addresses
        List<Address> addresses = addressRepository.findAllByUser(user);

        // 5. Add attributes to model
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("shipping", 2000); // fixed shipping
        model.addAttribute("finalPrice", total + 2000);
        model.addAttribute("addresses", addresses);

        return "checkout"; // Thymeleaf template: checkout.html
    }
}
