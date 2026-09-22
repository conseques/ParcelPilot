package com.parcelpilot.infrastructure.repository;

import com.parcelpilot.domain.model.Customer;
import com.parcelpilot.domain.port.CustomerRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer save(Customer customer) {
        customers.put(customer.id(), customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) { return Optional.ofNullable(customers.get(id)); }
}
