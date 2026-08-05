package com.example.order_service.service;

import com.example.order_service.model.Customer;
import com.example.order_service.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void findById_returnsCustomers_whenCustomerExists() {
        Customer customer = new Customer("Виталий", "89192121998", "vitalya334@yandex.ru");
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Customer result = customerService.findById(1);

        assertThat(result.getName()).isEqualTo("Виталий");
    }

    @Test
    void findById_throwsException_whenCustomerNotFound() {
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.findById(999));
    }
}
