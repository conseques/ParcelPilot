package com.parcelpilot.domain.port;

import com.parcelpilot.domain.model.Courier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourierRepository {
    Courier save(Courier courier);
    Optional<Courier> findById(UUID id);
    List<Courier> findAll();
}
