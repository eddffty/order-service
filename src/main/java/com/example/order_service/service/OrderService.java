package com.example.order_service.service;

import com.example.order_service.dto.OrderCreateRequest;
import com.example.order_service.dto.OrderUpdateRequest;
import com.example.order_service.model.Customer;
import com.example.order_service.model.Order;
import com.example.order_service.model.OrderItem;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, CustomerService customerService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.orderItemRepository = orderItemRepository;
    }

    public List<Order> findAll () {
        return orderRepository.findAll();
    }

    public Order findById (Integer id) {
        Optional<Order> result = orderRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        } else throw new RuntimeException("Заказ не найден");
    }

    public void save (OrderCreateRequest request) {
        Customer customer = customerService.findById(request.getCustomerId());
        Order order = new Order (customer, request.getStatus(), LocalDateTime.now());
        orderRepository.save(order);
    }

    public Order saveReadyOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateStatus(Integer id, OrderUpdateRequest request) {
        Order order = findById(id); // переиспользуем уже готовый метод
        order.setStatus(request.getStatus());
        return orderRepository.save(order);
    }

    public void deleteById (Integer id){
        orderRepository.deleteById(id);
    }

    public BigDecimal calculateTotalAmount(Integer orderId) {
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            BigDecimal itemSum = item.getPriceAtOrder().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemSum);
        }
        return total;
    }

    public Order updateFromRequest(Integer id, OrderCreateRequest request) {
        Order order = findById(id);
        Customer customer = customerService.findById(request.getCustomerId());
        order.setCustomer(customer);
        order.setStatus(request.getStatus());
        return orderRepository.save(order);
    }
}
