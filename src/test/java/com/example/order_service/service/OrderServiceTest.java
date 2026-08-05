package com.example.order_service.service;

import com.example.order_service.dto.OrderCreateRequest;
import com.example.order_service.model.Customer;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void save_createsOrder_withCorrectCustomer() {
        Customer customer = new Customer("Виталий", "89192121998", "vitalya334@yandex.ru");
        when(customerService.findById(1)).thenReturn(customer);

        OrderCreateRequest request = new OrderCreateRequest();
        request.setCustomerId(1);
        request.setStatus("NEW");

        orderService.save(request);

        verify(orderRepository).save(any(Order.class));
    }
}
