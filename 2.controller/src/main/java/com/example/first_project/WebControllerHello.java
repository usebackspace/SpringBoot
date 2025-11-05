package com.example.first_project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebControllerHello {
    
    @GetMapping("/webhello")
    public String webhello(){
        return "hello";    // returns home.html from templates/
    }
}
