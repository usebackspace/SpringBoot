package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.project.model.Cart;
import com.example.project.repository.CartRepository;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.UserRepository;

@Controller
@RequestMapping("/admin/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartController(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listCart(Model model) {
        model.addAttribute("cartItems", cartRepository.findAll());
        return "/admin/cart/view_list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("cart", new Cart());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "/admin/cart/add_list";
    }

    @PostMapping("/add")
    public String addCart(@ModelAttribute Cart cart) {
        cartRepository.save(cart);
        return "redirect:/admin/cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return "redirect:/admin/cart";
    }
}
