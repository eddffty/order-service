package com.example.order_service.controller;

import com.example.order_service.dto.OrderCreateRequest;
import com.example.order_service.dto.OrderUpdateRequest;
import com.example.order_service.model.Order;
import com.example.order_service.service.CustomerService;
import com.example.order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order getById (@PathVariable Integer id){
        return orderService.findById(id);
    }

    @PostMapping
    public void saveOrder (@RequestBody OrderCreateRequest orderCreateRequest){
        orderService.save(orderCreateRequest);
    }

//    @PutMapping("/{id}")
//    public void updateOrder (@PathVariable Integer id, @RequestBody Order order) {
//        Order newOrder = orderService.findById(id);
//        newOrder.setStatus(order.getStatus());
//        newOrder.setCustomer(order.getCustomer());
//        newOrder.setCreatedAt(order.getCreatedAt());
//        orderService.saveReadyOrder(newOrder);
//    }

    @DeleteMapping("/{id}")
    public void deleteOrderById (@PathVariable Integer id) {
        orderService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Integer id, @RequestBody OrderUpdateRequest request) {
        return orderService.updateStatus(id, request);
    }

    @GetMapping("/{id}/total")
    public BigDecimal getTotalAmount(@PathVariable Integer id) {
        return orderService.calculateTotalAmount(id);
    }
}
