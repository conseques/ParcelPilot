package com.parcelpilot.domain.service;

import com.parcelpilot.domain.exception.EntityNotFoundException;
import com.parcelpilot.domain.model.Customer;
import com.parcelpilot.domain.port.CustomerRepository;

import java.util.UUID;

public final class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) { this.repository = repository; }

    public Customer register(Customer customer) { return repository.save(customer); }

    public Customer get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
    }
}
