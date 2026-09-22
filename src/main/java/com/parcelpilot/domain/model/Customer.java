package com.parcelpilot.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class Customer {
    private final UUID id;
    private final String name;
    private final String phone;

    public Customer(String name, String phone) {
        this(UUID.randomUUID(), name, phone);
    }

    public Customer(UUID id, String name, String phone) {
        this.id = Objects.requireNonNull(id);
        this.name = requireText(name, "Name");
        this.phone = requireText(phone, "Phone");
    }

    public UUID id() { return id; }
    public String name() { return name; }
    public String phone() { return phone; }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(field + " must not be blank");
        return value.trim();
    }
}
