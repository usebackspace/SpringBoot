package com.example.petstore.controller;

import com.example.petstore.model.Pet;
import com.example.petstore.model.enums.Category;
import com.example.petstore.repository.PetRepository;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/pets")
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @GetMapping
    public String viewPets(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        return "admin/pet/view_pet";
    }

    @GetMapping("/add")
    public String addPetForm(Model model) {
        model.addAttribute("pet", new Pet());
        model.addAttribute("categories", Category.values());
        return "admin/pet/add_pet";
    }

    @PostMapping("/add")
    public String addPet(@ModelAttribute Pet pet,
                         @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            pet.setPetImage(imageFile.getBytes());
        }

        petRepository.save(pet);
        return "redirect:/admin/pets";
    }

    @GetMapping("/update/{id}")
    public String updatePetForm(@PathVariable Long id, Model model) {
        Pet pet = petRepository.findById(id).orElse(new Pet());
        model.addAttribute("pet", pet);
        model.addAttribute("categories", Category.values());
        return "admin/pet/update_pet";
    }

    @PostMapping("/update")
    public String updatePet(@ModelAttribute Pet pet,
                            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            pet.setPetImage(imageFile.getBytes());
        }

        petRepository.save(pet);
        return "redirect:/admin/pets";
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        petRepository.deleteById(id);
        return "redirect:/admin/pets";
    }

    @GetMapping("/images/{id}")
    @ResponseBody      // returned raw object directly to the HTTP response body not the template.
    public byte[] getImage(@PathVariable Long id) {
        Pet pet = petRepository.findById(id).orElseThrow();
        return pet.getPetImage();
    }
}
