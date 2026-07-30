package com.example.order_service.controller;

import com.example.order_service.model.Customer;
import com.example.order_service.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers (){
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Customer findById (@PathVariable Integer id) {
        return customerService.findById(id);
    }

    @PostMapping
    public void saveCustomer(@RequestBody Customer customer) {
        customerService.save(customer);
    }

    @PutMapping("/{id}")
    public void updateCustomer(@RequestBody Customer customer, @PathVariable Integer id) {
        Customer existing = customerService.findById(id);
        existing.setEmail(customer.getEmail());
        existing.setName(customer.getName());
        existing.setPhone(customer.getPhone());
        customerService.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable Integer id) {
        customerService.deleteById(id);
    }
}
