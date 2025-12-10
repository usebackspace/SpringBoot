package com.example.project.controller;

import com.example.project.model.Cart;
import com.example.project.model.Product;
import com.example.project.model.User;
import com.example.project.repository.CartRepository;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.UserRepository;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class UserCartController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public UserCartController(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId) {
        // 1. Get currently logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        // 2. Get product
        Product product = productRepository.findById(productId).orElseThrow();

        // 3. Check if product is already in cart
        Cart cartItem = cartRepository.findByUserAndProduct(user, product)
                .orElse(new Cart());

        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        // 4. Save to cart
        cartRepository.save(cartItem);

        return "redirect:/cart/view"; // Redirect to cart page
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        model.addAttribute("cartItems", cartRepository.findByUser(user));
        return "view_cart";
    }

      @GetMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return "redirect:/cart/view";
    }

    // Increment quantity
    @GetMapping("/addQuantity/{id}")
    public String addQuantity(@PathVariable Long id) {
        Cart cartItem = cartRepository.findById(id).orElseThrow();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartRepository.save(cartItem);
        return "redirect:/cart/view";
    }

    // Decrement quantity
    @GetMapping("/deleteQuantity/{id}")
    public String deleteQuantity(@PathVariable Long id) {
        Cart cartItem = cartRepository.findById(id).orElseThrow();

        // Decrement but not less than 1
        int newQty = cartItem.getQuantity() - 1;
        if (newQty < 1) {
            // Optionally, remove item from cart if quantity is zero
            cartRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(newQty);
            cartRepository.save(cartItem);
        }

        return "redirect:/cart/view";
    }
}

