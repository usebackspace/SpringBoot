package com.example.CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.CRUD.model.Avenger;
import com.example.CRUD.repository.AvengerRepository;


@Controller
public class AvengerController {

    private final AvengerRepository repo;
    
    public AvengerController(AvengerRepository repo){
        this.repo = repo;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("avengers", repo.findAll());
        model.addAttribute("avenger", new Avenger());         //Creates an empty Avenger object. i.e avenger (By default name of the object is same as class name)
        return "index";
    }

    @PostMapping("/addAvenger")
    public String addAvenger(@ModelAttribute Avenger avenger) {
        System.out.println("Adding avenger: " + avenger.getName());
        repo.save(avenger);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteAvenger(@PathVariable long id){
        repo.deleteById(id);
        return "redirect:/";
    }
 
    @GetMapping("/update/{id}")
    public String updateAvenger(@PathVariable long id, Model model) {
        // model.addAttribute("avenger", new Avenger());
        Avenger avenger =repo.findById(id).orElse(new Avenger());
        model.addAttribute("avenger", avenger);
        return "update"; 
    }

     @PostMapping("/updateAvenger")
    public String updateAvengerSubmit(@ModelAttribute Avenger avenger) {
        System.out.println("Adding avenger: " + avenger.getName());
        repo.save(avenger);
        return "redirect:/";
    }
}

// (@ModelAttribute Avenger avenger):
//     Spring automatically
//     1. Reads request parameters (name, heroic_name)
//     2. Creates a new Avenger object
//     3. Sets fields: avenger.setName(...), avenger.setHeroic_name(...)