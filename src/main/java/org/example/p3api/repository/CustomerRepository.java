package org.example.p3api.repository;

import org.example.p3api.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class CustomerRepository {
    private final Map<Long, Customer> customerMap=new ConcurrentHashMap<>() ;
    public void save(Customer customer)
    {
        customerMap.put(customer.getCustomerId(),customer);
    }

    public Collection<Customer> findAll() {
        return customerMap.values();
    }

    public Customer findById(Long id) {
        return customerMap.get(id);
    }
    public boolean existsById(Long id)
    {
        return customerMap.containsKey(id);
    }
}

