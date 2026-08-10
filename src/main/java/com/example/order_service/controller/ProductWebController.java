package com.example.order_service.controller;

import com.example.order_service.model.Product;
import com.example.order_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductWebController {
    private final ProductService productService;

    public ProductWebController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/page")
    public String listPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Product> productPage = productService.findAll(pageable);
        model.addAttribute("productPage", productPage);
        return "products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    @GetMapping("/products/{id}/edit")
    public String editProduct (Model model, @PathVariable Integer id) {
        model.addAttribute("product", productService.findById(id));
        return "product-form";
    }

    @PostMapping("/products")
    public String addProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        productService.save(product);
        return "redirect:/products/page";

    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteById(id);
        return "redirect:/products/page";
    }
}
