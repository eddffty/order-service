package com.example.order_service.controller;

import com.example.order_service.dto.OrderItemCreateRequest;
import com.example.order_service.model.OrderItem;
import com.example.order_service.service.OrderItemService;
import com.example.order_service.service.OrderService;
import com.example.order_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderItemController(OrderItemService orderItemService, OrderService orderService, ProductService productService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping
    public List<OrderItem> getAll() {
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public OrderItem findById(@PathVariable Integer id) {
        return orderItemService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody OrderItemCreateRequest orderItemCreateRequest) {
        orderItemService.save(orderItemCreateRequest);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody OrderItemCreateRequest orderItemCreateRequest, @PathVariable Integer id) {
        OrderItem orderItem = orderItemService.findById(id);
        orderItem.setProduct(productService.findById(orderItemCreateRequest.getProductId()));
        orderItem.setOrder(orderService.findById(orderItemCreateRequest.getOrderId()));
        orderItem.setPriceAtOrder(productService.findById(orderItemCreateRequest.getProductId()).getPrice());
        orderItem.setQuantity(orderItemCreateRequest.getQuantity());
        orderItemService.saveReadyOrderItem(orderItem);
    }

    @DeleteMapping("/{id}")
    public void deleteById (@PathVariable Integer id) {
        orderItemService.deleteOrderItemById(id);
    }
}
