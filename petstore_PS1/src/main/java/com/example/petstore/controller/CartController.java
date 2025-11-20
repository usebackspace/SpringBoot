package com.example.petstore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.petstore.model.Cart;
import com.example.petstore.repository.CartRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public CartController(CartRepository cartRepository, UserRepository userRepository, PetRepository petRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    @GetMapping
    public String listCart(Model model) {
        model.addAttribute("cartItems", cartRepository.findAll());
        return "cart/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("cart", new Cart());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("pets", petRepository.findAll());
        return "cart/add";
    }

    @PostMapping("/add")
    public String addCart(@ModelAttribute Cart cart) {
        cartRepository.save(cart);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return "redirect:/cart";
    }
}
