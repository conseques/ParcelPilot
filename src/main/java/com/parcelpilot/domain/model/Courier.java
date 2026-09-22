package com.parcelpilot.domain.model;

import com.parcelpilot.domain.model.value.GeoPoint;

import java.util.Objects;
import java.util.UUID;

public final class Courier {
    private final UUID id;
    private final String name;
    private final VehicleType vehicleType;
    private GeoPoint currentPosition;
    private boolean available;

    public Courier(String name, VehicleType vehicleType, GeoPoint currentPosition) {
        this(UUID.randomUUID(), name, vehicleType, currentPosition);
    }

    public Courier(UUID id, String name, VehicleType vehicleType, GeoPoint currentPosition) {
        this.id = Objects.requireNonNull(id);
        this.name = requireText(name, "Name");
        this.vehicleType = Objects.requireNonNull(vehicleType);
        this.currentPosition = Objects.requireNonNull(currentPosition);
        this.available = true;
    }

    public UUID id() { return id; }
    public String name() { return name; }
    public VehicleType vehicleType() { return vehicleType; }
    public GeoPoint currentPosition() { return currentPosition; }
    public boolean isAvailable() { return available; }

    public void moveTo(GeoPoint position) {
        this.currentPosition = Objects.requireNonNull(position);
    }

    public void markBusy() { available = false; }
    public void markAvailable() { available = true; }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}
