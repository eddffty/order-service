package com.example.order_service.service;

import com.example.order_service.dto.OrderItemCreateRequest;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import com.example.order_service.model.Product;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final OrderService orderService;

    public OrderItemService(OrderItemRepository orderItemRepository, ProductService productService, OrderService orderService) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.orderService = orderService;
    }

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem findById(Integer id) {
        Optional<OrderItem> result = orderItemRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else throw new RuntimeException("Позиция не найдена");
    }

    public void save(OrderItemCreateRequest orderItemCreateRequest) {
        Integer quantity = orderItemCreateRequest.getQuantity();
        Product product = productService.findById(orderItemCreateRequest.getProductId());
        Order order = orderService.findById(orderItemCreateRequest.getOrderId());
        BigDecimal priceAtOrder = product.getPrice();
        OrderItem orderItem = new OrderItem(product, order, quantity, priceAtOrder);
        orderItemRepository.save(orderItem);

    }

    public void saveReadyOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void deleteOrderItemById(Integer id) {
        orderItemRepository.deleteById(id);
    }
}
