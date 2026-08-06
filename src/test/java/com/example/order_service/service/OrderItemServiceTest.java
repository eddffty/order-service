package com.example.order_service.service;

import com.example.order_service.dto.OrderItemCreateRequest;
import com.example.order_service.model.Customer;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import com.example.order_service.model.Product;
import com.example.order_service.repository.OrderItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductService productService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    void save_createsOrderItem_withCorrectPrice() {
        Product product = new Product(1, "Круассан", new BigDecimal("120.00"), 15);
        Customer customer = new Customer("Иван", "89991234567", "ivan@test.ru");
        Order order = new Order(customer, "NEW", LocalDateTime.now());
        when(orderService.findById(1)).thenReturn(order);
        when(productService.findById(1)).thenReturn(product);
        OrderItemCreateRequest request = new OrderItemCreateRequest();
        request.setProductId(1);
        request.setOrderId(1);
        request.setQuantity(3);

        orderItemService.save(request);

        verify(orderItemRepository).save(any(OrderItem.class));
    }
}