package com.parcelpilot.domain.service;

import com.parcelpilot.domain.model.Customer;
import com.parcelpilot.domain.model.DeliveryPriority;
import com.parcelpilot.domain.model.Parcel;
import com.parcelpilot.domain.model.value.Address;
import com.parcelpilot.domain.model.value.GeoPoint;

import java.util.Objects;

public record DeliveryRequest(
        Customer customer,
        Address pickupAddress,
        Address destination,
        GeoPoint pickupPoint,
        GeoPoint destinationPoint,
        Parcel parcel,
        DeliveryPriority priority
) {
    public DeliveryRequest {
        Objects.requireNonNull(customer);
        Objects.requireNonNull(pickupAddress);
        Objects.requireNonNull(destination);
        Objects.requireNonNull(pickupPoint);
        Objects.requireNonNull(destinationPoint);
        Objects.requireNonNull(parcel);
        Objects.requireNonNull(priority);
    }
}
