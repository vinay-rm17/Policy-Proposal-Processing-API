package org.example.p3api.service;

import org.example.p3api.model.Customer;
import org.example.p3api.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AtomicLong customerIdGenerator=new AtomicLong(1000);
    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository=customerRepository;
    }
    public Customer createCustomer(Customer customer) {
        validateCustomer(customer);
         customer.setCustomerId(customerIdGenerator.incrementAndGet());
         customerRepository.save(customer);
         return customer;
    }

    public Collection<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        Customer customer=customerRepository.findById(id);
        if(customer==null)
        {
            throw new RuntimeException("Customer not found");

        }
        return customer;
    }
    public Customer updateCustomer(Long id,Customer customer)
    {
        if(!customerRepository.existsById(id))
        {
            throw new RuntimeException("Customer not found");
        }
        validateCustomer(customer);
        customer.setCustomerId(id);
        customerRepository.save(customer);
        return customer;
    }
    private void validateCustomer(Customer customer) {

        if (customer.getAge() < 18 || customer.getAge() > 65) {
            throw new RuntimeException("Age should be between 18 and 65");
        }

        if (customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            throw new RuntimeException("First Name is mandatory");
        }

        if (customer.getLastName() == null || customer.getLastName().isBlank()) {
            throw new RuntimeException("Last Name is mandatory");
        }

        if (customer.getMobileNumber() == null || customer.getMobileNumber().isBlank()) {
            throw new RuntimeException("Mobile Number is mandatory");
        }
    }
}
