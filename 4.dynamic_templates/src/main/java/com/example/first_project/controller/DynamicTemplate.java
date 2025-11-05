package com.example.first_project.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DynamicTemplate {
    
    @GetMapping("/")
    public String index(Model model){  
        model.addAttribute("name", "Steve Roger");   //model.addAttribute("key", value)
        model.addAttribute("heroic_name","Captain America");

        // If Condition
        model.addAttribute("isHero", true);

        // If ELSE Condition
        model.addAttribute("isHero", false);

        // Switch Case
        model.addAttribute("herotype", "guardin");

        // Loop 
        List<String> avengers = new ArrayList<>();
        avengers.add("Captain America");
        avengers.add("Ironman");
        avengers.add("Thor");
        avengers.add("Hulk");
        
        model.addAttribute("aven", avengers);

        return "index";   // Template name
    }
}
