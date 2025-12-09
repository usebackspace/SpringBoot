package com.example.petstore.controller;

import com.example.petstore.model.Order;
import com.example.petstore.repository.OrderRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.UserRepository;
import com.example.petstore.repository.AddressRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final PetRepository petRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public OrderController(OrderRepository orderRepository, PetRepository petRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.petRepository = petRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());  
        return "admin/order/view_order";  
    }

    @GetMapping("/add")
    public String addOrderForm(Model model) {
        model.addAttribute("order", new Order());  
        model.addAttribute("pets", petRepository.findAll()); 
        model.addAttribute("addresses", addressRepository.findAll());
        model.addAttribute("users", userRepository.findAll());  
        return "admin/order/add_order";  
    }

    
    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order) {
        orderRepository.save(order);  // Save the new order
        return "redirect:/admin/orders"; 
    }

    @GetMapping("/update/{id}")
    public String updateOrderForm(@PathVariable Long id, Model model) {
        // Retrieve the order by ID, or create a new Order object if not found
        Order order = orderRepository.findById(id).orElse(new Order());
        
        model.addAttribute("order", order);
        model.addAttribute("pets", petRepository.findAll());  // Add all available pets
        model.addAttribute("addresses", addressRepository.findAll());  // Add all available addresses
        return "admin/order/update_order";  
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute Order order) {
        orderRepository.save(order);  
        return "redirect:/admin/orders";  
    }

    // Delete an order
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);  
        return "redirect:/admin/orders";  
    }
}
