package com.example.order_service.controller;

import com.example.order_service.dto.OrderCreateRequest;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderStatus;
import com.example.order_service.service.CustomerService;
import com.example.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String listPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Order> orderPage = orderService.findAll(pageable);
        Map<Integer, BigDecimal> totals = new HashMap<>();
        for (Order order : orderPage) {
            totals.put(order.getId(), orderService.calculateTotalAmount(order.getId()));
        }
        model.addAttribute("orderPage", orderPage);
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
    public String addOrder(@Valid @ModelAttribute OrderCreateRequest orderRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            return "order-form";
        }
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
