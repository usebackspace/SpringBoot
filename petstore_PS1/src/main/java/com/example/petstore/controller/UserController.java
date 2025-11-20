package com.example.petstore.controller;

import com.example.petstore.model.User;
import com.example.petstore.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Display all users
    @GetMapping
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());  // List of all users
        return "admin/user/view_user";  
    }

    // Show form to add a new user
    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());  // Add an empty user object for the form
        return "admin/user/add_user";  
    }

    // Handle form submission for adding a new user
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userRepository.save(user);  // Save the new user
        return "redirect:/admin/users";  
    }

    // Show form to update an existing user
    @GetMapping("/update/{id}")
    public String updateUserForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(new User());
        model.addAttribute("user", user);
        return "admin/user/update_user";  // Show the update form
    }

    // Handle form submission for updating an existing user
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userRepository.save(user);  // Save the updated user
        return "redirect:/admin/users";  
    }

    // Delete a user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);  // Delete user by ID
        return "redirect:/admin/users";  
    }
}
