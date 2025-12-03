package com.example.security.controller;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SecurityController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String index() {
        return "index";  // your index.html
    }

    // @GetMapping("/login")
    // public String login() {
    //     return "login";  // your login.html
    // }
    
    @GetMapping("/login")
    public String login(Authentication auth) {
        if(auth.isAuthenticated()){            
            return "redirect:/";    // redirect logged-in users to home page
        }
        return "login";  // your login.html
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";  // your register.html
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password) {

        // Create a new user with default role USER
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        // Save to MySQL
        userRepository.save(user);

        // Redirect to login page after registration
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
    @GetMapping("/avenger")
    public String avenger(){
        return "avenger";
    }
}
