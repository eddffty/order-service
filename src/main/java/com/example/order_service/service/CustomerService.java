package com.example.order_service.service;

import com.example.order_service.model.Customer;
import com.example.order_service.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer findById(Integer id) {
        Optional<Customer> result = customerRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            log.error("Покупатель с id={} не найден", id);
            throw new RuntimeException("Покупатель не найден");
        }
    }

    public void save(Customer customer) {
        log.info("Создание покупателя");
        customerRepository.save(customer);
        log.info("Покупатель с id={} создан", customer.getId());
    }

    public void deleteById(Integer id) {
        log.warn("Собирается удалить покупателя с id={}", id);
        customerRepository.deleteById(id);
        log.info("Покупатель c id={} удален", id);
    }
}
