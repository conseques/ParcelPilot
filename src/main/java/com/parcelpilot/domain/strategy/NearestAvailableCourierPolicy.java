package com.parcelpilot.domain.strategy;

import com.parcelpilot.domain.exception.NoCourierAvailableException;
import com.parcelpilot.domain.model.Courier;
import com.parcelpilot.domain.model.Delivery;

import java.util.Comparator;
import java.util.List;

public final class NearestAvailableCourierPolicy implements CourierAssignmentPolicy {
    @Override
    public Courier chooseCourier(Delivery delivery, List<Courier> couriers) {
        return couriers.stream()
                .filter(Courier::isAvailable)
                .filter(courier -> courier.vehicleType().maxWeightKg() >= delivery.parcel().weightKg())
                .min(Comparator.comparingDouble(courier ->
                        courier.currentPosition().distanceTo(delivery.pickupPoint())))
                .orElseThrow(() -> new NoCourierAvailableException("No available courier can carry this parcel"));
    }
}
