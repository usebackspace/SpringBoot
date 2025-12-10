package com.example.petstore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.petstore.model.Pet;
import com.example.petstore.model.enums.Category;
import com.example.petstore.repository.PetRepository;

@Controller
public class CoreController {

    private final PetRepository petRepository;

    public CoreController(PetRepository petRepository){
        this.petRepository =petRepository;
    }
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
    public String dogCategories(Model model) {
        model.addAttribute("pets", petRepository.findByCategory(Category.DOG));
        return "dog_categories";  // templates/dog_categories.html
    }
    
    @GetMapping("/cats")
    public String catCategories(Model model) {
        model.addAttribute("pets", petRepository.findByCategory(Category.CAT));
        return "cat_categories";  // templates/cat_categories.html
    }

    @GetMapping("/birds")
    public String birdCategories(Model model) {
        model.addAttribute("pets", petRepository.findByCategory(Category.BIRD));
        return "bird_categories";  // templates/bird_categories.html
    }

    // =========== For Image =========

    @GetMapping("/images/{id}")
    @ResponseBody      // returned raw object directly to the HTTP response body not the template.
    public byte[] getImage(@PathVariable long id) {
        Pet pet = petRepository.findById(id).orElseThrow();
        return pet.getPetImage();
    }

    // ==============================


    @GetMapping("/pet-details/{id}")
    public String petDetails(@PathVariable long id,Model model) {
        Pet pet = petRepository.findById(id).orElseThrow();
        model.addAttribute("pets", pet);
        return "pet_details";  // templates/pet_details.html
    }
}
