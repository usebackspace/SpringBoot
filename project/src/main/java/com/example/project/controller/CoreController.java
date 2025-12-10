package com.example.project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project.model.Product;
import com.example.project.model.enums.Category;
import com.example.project.repository.ProductRepository;

@Controller
public class CoreController {

    private final ProductRepository productRepository;

    public CoreController(ProductRepository productRepository){
        this.productRepository =productRepository;
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

    @GetMapping("/cat_1")
    public String cat1Categories(Model model) {
        model.addAttribute("products", productRepository.findByCategory(Category.CAT_1));
        return "cat_1";  // templates/cat1_categories.html
    }
    
    @GetMapping("/cat_2")
    public String catCategories(Model model) {
        model.addAttribute("products", productRepository.findByCategory(Category.CAT_2));
        return "cat_2";  // templates/cat_categories.html
    }

    @GetMapping("/cat_3")
    public String birdCategories(Model model) {
        model.addAttribute("products", productRepository.findByCategory(Category.CAT_3));
        return "cat_3";  // templates/bird_categories.html
    }

    // =========== For Image =========

    @GetMapping("/images/{id}")
    @ResponseBody      // returned raw object directly to the HTTP response body not the template.
    public byte[] getImage(@PathVariable long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return product.getProductImage();
    }

    // ==============================


    @GetMapping("/product-details/{id}")
    public String prodctDetails(@PathVariable long id,Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("products", product);
        return "product_details";  // templates/product_details.html
    }
}
