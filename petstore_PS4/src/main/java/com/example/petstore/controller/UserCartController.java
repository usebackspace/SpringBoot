package com.example.petstore.controller;

import com.example.petstore.model.Cart;
import com.example.petstore.model.Pet;
import com.example.petstore.model.User;
import com.example.petstore.repository.CartRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;


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
    private final PetRepository petRepository;

    public UserCartController(CartRepository cartRepository, UserRepository userRepository, PetRepository petRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long petId) {
        // 1. Get currently logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        // 2. Get pet
        Pet pet = petRepository.findById(petId).orElseThrow();

        // 3. Check if pet is already in cart
        Cart cartItem = cartRepository.findByUserAndPet(user, pet)
                .orElse(new Cart());

        cartItem.setUser(user);
        cartItem.setPet(pet);
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

