package com.example.order_service.controller;

import com.example.order_service.dto.OrderItemCreateRequest;
import com.example.order_service.model.OrderItem;
import com.example.order_service.service.OrderItemService;
import com.example.order_service.service.OrderService;
import com.example.order_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderItemWebController {
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderItemWebController(OrderItemService orderItemService, OrderService orderService, ProductService productService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/order-items/page")
    public String listPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<OrderItem> orderItemPage = orderItemService.findAll(pageable);
        model.addAttribute("orderItemPage", orderItemPage);
        return "order-items";
    }

    @GetMapping("/order-items/new")
    public String newOrderItem(Model model) {
        model.addAttribute("itemRequest", new OrderItemCreateRequest());
        model.addAttribute("products", productService.findAll());
        model.addAttribute("orders", orderService.findAll());
        return "order-item-form";
    }

    @PostMapping("/order-items")
    public String addOrderItem(@Valid @ModelAttribute OrderItemCreateRequest itemRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orders", orderService.findAll());
            model.addAttribute("products", productService.findAll());
            return "order-item-form";
        }
        orderItemService.save(itemRequest);
        return "redirect:/order-items/page";
    }


    @PostMapping("/order-items/{id}/delete")
    public String deleteOrder(@PathVariable Integer id) {
        orderItemService.deleteOrderItemById(id);
        return "redirect:/order-items/page";
    }
}
