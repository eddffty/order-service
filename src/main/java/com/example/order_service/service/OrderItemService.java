package com.example.order_service.service;

import com.example.order_service.dto.OrderItemCreateRequest;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import com.example.order_service.model.Product;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final OrderService orderService;
    private static final Logger log = LoggerFactory.getLogger(OrderItemService.class);

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
        } else {
            log.error("Позиция с id={} не найдена", id);
            throw new RuntimeException("Позиция не найдена");
        }
    }

    @Transactional
    public void save(OrderItemCreateRequest orderItemCreateRequest) {
        log.info("Добавление позиции в заказ с id={}", orderItemCreateRequest.getOrderId());
        Integer quantity = orderItemCreateRequest.getQuantity();
        Product product = productService.findById(orderItemCreateRequest.getProductId());
        if (product.getStockQty() < quantity){
            log.error("Позиции {} недостаточно на складе", product.getName());
            throw new RuntimeException("Недостаточно товара на складе");
        }
        Order order = orderService.findById(orderItemCreateRequest.getOrderId());
        BigDecimal priceAtOrder = product.getPrice();
        OrderItem orderItem = new OrderItem(product, order, quantity, priceAtOrder);
        orderItemRepository.save(orderItem);
        product.setStockQty(product.getStockQty() - quantity);
        productService.save(product);
        log.info("Позиция добавлена");
    }

    public void saveReadyOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void deleteOrderItemById(Integer id) {
        log.warn("Собирается удалить позицию с id={}", id);
        orderItemRepository.deleteById(id);
        log.info("Позиция с id={} удалена", id);
    }
}
