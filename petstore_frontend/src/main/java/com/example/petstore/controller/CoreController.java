package com.example.petstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoreController {

    @GetMapping("/")
    public String index() {
        return "index";  // templates/home.html
    }

    @GetMapping("/about")
    public String about() {
        return "about";  // templates/about.html
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";  // templates/contact.html
    }

    @GetMapping("/dogs")
    public String dogCategories() {
        return "dog_categories";  // templates/dog_categories.html
    }

    @GetMapping("/cats")
    public String catCategories() {
        return "cat_categories";  // templates/cat_categories.html
    }

    @GetMapping("/birds")
    public String birdCategories() {
        return "bird_categories";  // templates/bird_categories.html
    }

    @GetMapping("/pet-details")
    public String petDetails() {
        return "pet_details";  // templates/pet_details.html
    }
}
