package com.parcelpilot.infrastructure.repository;

import com.parcelpilot.domain.model.Courier;
import com.parcelpilot.domain.port.CourierRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryCourierRepository implements CourierRepository {
    private final Map<UUID, Courier> couriers = new ConcurrentHashMap<>();

    @Override
    public Courier save(Courier courier) {
        couriers.put(courier.id(), courier);
        return courier;
    }

    @Override
    public Optional<Courier> findById(UUID id) { return Optional.ofNullable(couriers.get(id)); }

    @Override
    public java.util.List<Courier> findAll() { return new ArrayList<>(couriers.values()); }
}
