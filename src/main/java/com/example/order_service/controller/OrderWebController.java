package com.example.order_service.controller;

import com.example.order_service.dto.OrderCreateRequest;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderStatus;
import com.example.order_service.service.CustomerService;
import com.example.order_service.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderWebController {
    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderWebController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping("/orders/page")
    public String listPage(Model model) {
        List<Order> orders = orderService.findAll();
        Map<Integer, BigDecimal> totals = new HashMap<>();
        for (Order order : orders) {
            totals.put(order.getId(), orderService.calculateTotalAmount(order.getId()));
        }
        model.addAttribute("orders", orders);
        model.addAttribute("totals", totals);
        return "orders";
    }

    @GetMapping("/orders/new")
    public String newOrder(Model model) {
        model.addAttribute("orderRequest", new OrderCreateRequest());
        model.addAttribute("customers", customerService.findAll());
        return "order-form";
    }


    @PostMapping("/orders")
    public String addOrder(@ModelAttribute OrderCreateRequest orderRequest) {
        if (orderRequest.getId() == null) {
            orderService.save(orderRequest);
        } else {
            orderService.updateFromRequest(orderRequest.getId(), orderRequest);
        }
        return "redirect:/orders/page";
    }

    @GetMapping("/orders/{id}/edit")
    public String editOrder(Model model, @PathVariable Integer id) {
        Order order = orderService.findById(id);
        OrderCreateRequest orderRequest = new OrderCreateRequest();
        orderRequest.setId(order.getId());
        orderRequest.setCustomerId(order.getCustomer().getId());
        String status = String.valueOf(order.getStatus());
        orderRequest.setStatus(status);
        model.addAttribute("orderRequest", orderRequest);
        model.addAttribute("customers", customerService.findAll());
        return "order-form";
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteById(id);
        return "redirect:/orders/page";
    }
}
