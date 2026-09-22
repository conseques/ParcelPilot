package com.parcelpilot.infrastructure.repository;

import com.parcelpilot.domain.model.Delivery;
import com.parcelpilot.domain.port.DeliveryRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryDeliveryRepository implements DeliveryRepository {
    private final Map<UUID, Delivery> deliveries = new ConcurrentHashMap<>();

    @Override
    public Delivery save(Delivery delivery) {
        deliveries.put(delivery.id(), delivery);
        return delivery;
    }

    @Override
    public Optional<Delivery> findById(UUID id) { return Optional.ofNullable(deliveries.get(id)); }

    @Override
    public java.util.List<Delivery> findAll() { return new ArrayList<>(deliveries.values()); }
}
