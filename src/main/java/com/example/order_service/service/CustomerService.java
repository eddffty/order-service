package com.example.order_service.service;

import com.example.order_service.model.Customer;
import com.example.order_service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Integer id) {
        Optional<Customer> result = customerRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else throw new RuntimeException("Покупатель не найден");
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }
}
