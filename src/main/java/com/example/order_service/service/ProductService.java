package com.example.order_service.service;

import com.example.order_service.model.Product;
import com.example.order_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll (){
        return productRepository.findAll();
    }

    public Product findById (Integer id){
        Optional<Product> result = productRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        } else throw new RuntimeException("Товар не найден");

    }

    public void save (Product product){
        productRepository.save(product);
    }

    public void deleteById(Integer id){
        productRepository.deleteById(id);
    }


}
