package org.example.p3api.service;

import org.example.p3api.model.Customer;
import org.example.p3api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        customerRepository = new CustomerRepository();
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void shouldCreateCustomerSuccessfully() {

        Customer customer = new Customer();

        customer.setFirstName("Vinay");
        customer.setLastName("RM");
        customer.setAge(23);
        customer.setGender("Male");
        customer.setEmail("vinay@gmail.com");
        customer.setMobileNumber("9876543210");
        customer.setPanNumber("ABCDE1234F");

        Customer saved = customerService.createCustomer(customer);

        assertNotNull(saved.getCustomerId());
        assertEquals("Vinay", saved.getFirstName());
        assertEquals("RM", saved.getLastName());
        assertEquals(23, saved.getAge());
    }

    @Test
    void shouldThrowExceptionWhenAgeLessThan18() {

        Customer customer = new Customer();

        customer.setFirstName("Vinay");
        customer.setLastName("RM");
        customer.setAge(17);
        customer.setGender("Male");
        customer.setEmail("vinay@gmail.com");
        customer.setMobileNumber("9876543210");
        customer.setPanNumber("ABCDE1234F");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.createCustomer(customer)
        );

        assertEquals(
                "Age should be between 18 and 65",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenAgeGreaterThan65() {

        Customer customer = new Customer();

        customer.setFirstName("Vinay");
        customer.setLastName("RM");
        customer.setAge(70);
        customer.setGender("Male");
        customer.setEmail("vinay@gmail.com");
        customer.setMobileNumber("9876543210");
        customer.setPanNumber("ABCDE1234F");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.createCustomer(customer)
        );

        assertEquals(
                "Age should be between 18 and 65",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenFirstNameIsMissing() {

        Customer customer = new Customer();

        customer.setFirstName("");
        customer.setLastName("RM");
        customer.setAge(23);
        customer.setGender("Male");
        customer.setEmail("vinay@gmail.com");
        customer.setMobileNumber("9876543210");
        customer.setPanNumber("ABCDE1234F");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.createCustomer(customer)
        );

        assertEquals(
                "First Name is mandatory",
                exception.getMessage()
        );
    }
}