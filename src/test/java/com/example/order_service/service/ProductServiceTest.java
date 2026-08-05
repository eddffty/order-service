package com.example.order_service.service;

import com.example.order_service.model.Product;
import com.example.order_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void findById_returnsProduct_whenProductExists() {
        Product product = new Product(1, "Капучино", new BigDecimal("250.00"), 50);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product result = productService.findById(1);

        assertThat(result.getName()).isEqualTo("Капучино");
    }
}