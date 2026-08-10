package com.example.order_service.service;

import com.example.order_service.model.Product;
import com.example.order_service.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll (){
        return productRepository.findAll();
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findById (Integer id){

        Optional<Product> result = productRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        } else {
            log.error("Товар с id={} не найден", id);
            throw new RuntimeException("Товар не найден");
        }

    }

    public void save (Product product){
        log.info("Создание товара {}", product.getName());
        productRepository.save(product);
        log.info("Товар с id={} успешно создан", product.getId());
    }

    public void deleteById(Integer id){
        log.warn("Собирается удалить товар с id={}", id);
        productRepository.deleteById(id);
        log.info("Товар с id={} удален", id);
    }


}
