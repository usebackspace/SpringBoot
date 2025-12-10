package com.example.project.controller;

import com.example.project.model.Address;
import com.example.project.model.Cart;
import com.example.project.model.Order;
import com.example.project.model.User;
import com.example.project.model.enums.OrderStatus;
import com.example.project.repository.AddressRepository;
import com.example.project.repository.CartRepository;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserOrderController {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserOrderController(OrderRepository orderRepository,
                               CartRepository cartRepository,
                               UserRepository userRepository,
                               AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @PostMapping("/order/cod")
    public String placeCodOrder(@RequestParam("addressId") Long addressId) {
        // 1. Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Load address
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // 3. Get all cart items of the user
        List<Cart> cartItems = cartRepository.findByUser(user);

        // 4. Create order for each cart item
        for (Cart cart : cartItems) {
            Order order = new Order();
            order.setUser(user);
            order.setProduct(cart.getProduct());
            order.setQuantity(cart.getQuantity());
            order.setAddress(address);
            order.setOrderAt(LocalDateTime.now());
            order.setStatus(OrderStatus.PENDING);

            orderRepository.save(order);
        }

        // 5. Clear cart after placing order
        cartRepository.deleteAll(cartItems);

        return "redirect:/order/success";
    }

    @GetMapping("/order/success")
    public String success(Model model) {
        // Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUserOrderByOrderAtDesc(user);
        model.addAttribute("orders", orders);

        return "success";
    }

    @GetMapping("/order")
    public String order(Model model) {
        // Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUserOrderByOrderAtDesc(user);
        model.addAttribute("orders", orders);

        return "order";
    }
}
