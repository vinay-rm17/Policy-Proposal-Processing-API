package org.example.p3api.controller;

import org.example.p3api.model.Customer;
import org.example.p3api.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/customers")
public class CustomoerController {
    private final CustomerService customerService;
    public CustomoerController(CustomerService customerService)
    {
        this.customerService=customerService;
    }
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer)
    {
        return customerService.createCustomer(customer);
    }
    @GetMapping
    public Collection<Customer> getAllCustomers()
    {
        return customerService.getAllCustomers();
    }
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id)
    {
        return customerService.getCustomerById(id);
    }
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id,
                                   @RequestBody Customer customer) {

        return customerService.updateCustomer(id, customer);
    }

}
