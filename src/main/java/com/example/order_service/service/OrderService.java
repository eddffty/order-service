package com.example.order_service.service;

import com.example.order_service.dto.OrderCreateRequest;
import com.example.order_service.dto.OrderUpdateRequest;
import com.example.order_service.model.*;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, CustomerService customerService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.orderItemRepository = orderItemRepository;
    }

    public List<Order> findAll () {
        return orderRepository.findAll();
    }

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order findById (Integer id) {
        Optional<Order> result = orderRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        } else {
            log.error("Заказ с id={} не найден", id);
            throw new RuntimeException("Заказ не найден");
        }
    }

    public void save (OrderCreateRequest request) {
        log.info("Создание заказа для покупателя id={}", request.getCustomerId());
        Customer customer = customerService.findById(request.getCustomerId());
        OrderStatus status = OrderStatus.valueOf(request.getStatus());
        Order order = new Order (customer, status, LocalDateTime.now());
        orderRepository.save(order);
        log.info("Заказ успешно создан: id={}", order.getId());
    }

    public Order saveReadyOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateStatus(Integer id, OrderUpdateRequest request) {
        Order order = findById(id);
        OrderStatus status = OrderStatus.valueOf(request.getStatus());
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteById (Integer id){
        log.warn("Собираюсь удалить заказ с id={}", id);
        orderRepository.deleteById(id);
        log.info("Заказ id={} успешно удален", id);
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
        OrderStatus status = OrderStatus.valueOf(request.getStatus());
        order.setCustomer(customer);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
