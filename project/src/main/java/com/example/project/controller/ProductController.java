package com.example.project.controller;

import com.example.project.model.Product;
import com.example.project.model.enums.Category;
import com.example.project.repository.ProductRepository;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String viewProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/product/view_product";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", Category.values());
        return "admin/product/add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product,
                         @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            product.setProductImage(imageFile.getBytes());
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElse(new Product());
        model.addAttribute("product", product);
        model.addAttribute("categories", Category.values());
        return "admin/product/update_product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product,
                            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            product.setProductImage(imageFile.getBytes());
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/images/{id}")
    @ResponseBody      // returned raw object directly to the HTTP response body not the template.
    public byte[] getImage(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return product.getProductImage();
    }
}
