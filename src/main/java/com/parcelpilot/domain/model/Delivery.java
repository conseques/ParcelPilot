package com.parcelpilot.domain.model;

import com.parcelpilot.domain.exception.InvalidDeliveryStateException;
import com.parcelpilot.domain.model.value.Address;
import com.parcelpilot.domain.model.value.GeoPoint;
import com.parcelpilot.domain.model.value.Money;

import java.time.Clock;
import java.time.Instant;
import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

public final class Delivery {
    private final UUID id;
    private final Customer customer;
    private final Address pickupAddress;
    private final Address destination;
    private final GeoPoint pickupPoint;
    private final GeoPoint destinationPoint;
    private final Parcel parcel;
    private final DeliveryPriority priority;
    private final Money price;
    private final Instant createdAt;
    private DeliveryStatus status;
    private Courier courier;

    public Delivery(Customer customer, Address pickupAddress, Address destination,
                    GeoPoint pickupPoint, GeoPoint destinationPoint, Parcel parcel,
                    DeliveryPriority priority, Money price, Clock clock) {
        this.id = UUID.randomUUID();
        this.customer = Objects.requireNonNull(customer);
        this.pickupAddress = Objects.requireNonNull(pickupAddress);
        this.destination = Objects.requireNonNull(destination);
        this.pickupPoint = Objects.requireNonNull(pickupPoint);
        this.destinationPoint = Objects.requireNonNull(destinationPoint);
        this.parcel = Objects.requireNonNull(parcel);
        this.priority = Objects.requireNonNull(priority);
        this.price = Objects.requireNonNull(price);
        this.createdAt = Instant.now(Objects.requireNonNull(clock));
        this.status = DeliveryStatus.CREATED;
    }

    public UUID id() { return id; }
    public Customer customer() { return customer; }
    public Address pickupAddress() { return pickupAddress; }
    public Address destination() { return destination; }
    public GeoPoint pickupPoint() { return pickupPoint; }
    public GeoPoint destinationPoint() { return destinationPoint; }
    public Parcel parcel() { return parcel; }
    public DeliveryPriority priority() { return priority; }
    public Money price() { return price; }
    public Instant createdAt() { return createdAt; }
    public DeliveryStatus status() { return status; }
    public Courier courier() { return courier; }

    public void assignTo(Courier courier) {
        ensureStatus(DeliveryStatus.CREATED);
        this.courier = Objects.requireNonNull(courier);
        this.status = DeliveryStatus.ASSIGNED;
    }

    public void moveTo(DeliveryStatus nextStatus) {
        Objects.requireNonNull(nextStatus);
        if (!allowedTransitions().contains(nextStatus)) {
            throw new InvalidDeliveryStateException("Cannot move delivery from " + status + " to " + nextStatus);
        }
        status = nextStatus;
    }

    private void ensureStatus(DeliveryStatus expected) {
        if (status != expected) throw new InvalidDeliveryStateException("Expected status " + expected + ", but was " + status);
    }

    private EnumSet<DeliveryStatus> allowedTransitions() {
        return switch (status) {
            case CREATED -> EnumSet.of(DeliveryStatus.ASSIGNED, DeliveryStatus.CANCELLED);
            case ASSIGNED -> EnumSet.of(DeliveryStatus.PICKED_UP, DeliveryStatus.CANCELLED);
            case PICKED_UP -> EnumSet.of(DeliveryStatus.IN_TRANSIT);
            case IN_TRANSIT -> EnumSet.of(DeliveryStatus.DELIVERED);
            case DELIVERED, CANCELLED -> EnumSet.noneOf(DeliveryStatus.class);
        };
    }
}
