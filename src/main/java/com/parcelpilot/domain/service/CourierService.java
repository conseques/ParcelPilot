package com.parcelpilot.domain.service;

import com.parcelpilot.domain.exception.EntityNotFoundException;
import com.parcelpilot.domain.model.Courier;
import com.parcelpilot.domain.port.CourierRepository;

import java.util.List;
import java.util.UUID;

public final class CourierService {
    private final CourierRepository repository;

    public CourierService(CourierRepository repository) { this.repository = repository; }

    public Courier register(Courier courier) { return repository.save(courier); }

    public Courier get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courier not found: " + id));
    }

    public List<Courier> findAll() { return repository.findAll(); }
}
