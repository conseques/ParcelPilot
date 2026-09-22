package com.parcelpilot.domain.port;

import com.parcelpilot.domain.model.Delivery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);
    Optional<Delivery> findById(UUID id);
    List<Delivery> findAll();
}
