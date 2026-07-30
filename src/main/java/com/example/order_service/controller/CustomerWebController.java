package com.example.order_service.controller;
import com.example.order_service.model.Customer;
import com.example.order_service.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerWebController {

    private final CustomerService customerService;

    public CustomerWebController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers/page")
    public String listPage(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customers";
    }

    @GetMapping("/customers/new")
    public String newCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    @GetMapping("/customers/{id}/edit")
    public String editCustomer (Model model, @PathVariable Integer id) {
        model.addAttribute("customer", customerService.findById(id));
        return "customer-form";
    }

    @PostMapping("/customers")
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/customers/page";
    }

    @PostMapping("/customers/{id}/delete")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.deleteById(id);
        return "redirect:/customers/page";
    }
}
