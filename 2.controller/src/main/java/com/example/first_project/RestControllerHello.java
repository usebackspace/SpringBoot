package com.example.first_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerHello {
    @GetMapping("/resthello")
    public String RestHello(){
        return "Printing Hello using Rest Controller";   //The method returns a String, which becomes the HTTP response body.
    }
}
