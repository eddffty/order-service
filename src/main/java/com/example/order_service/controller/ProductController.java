package com.example.order_service.controller;

import com.example.order_service.model.Product;
import com.example.order_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.findById(id);
    }

    @PostMapping
    public void saveProduct(@RequestBody Product product) {
        productService.save(product);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        Product existing = productService.findById(id);
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setStockQty(product.getStockQty());
        productService.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Integer id) {
        productService.deleteById(id);
    }

}
