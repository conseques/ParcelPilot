package com.parcelpilot.domain.model;

public enum VehicleType {
    BICYCLE(5.0),
    SCOOTER(15.0),
    VAN(100.0);

    private final double maxWeightKg;

    VehicleType(double maxWeightKg) { this.maxWeightKg = maxWeightKg; }
    public double maxWeightKg() { return maxWeightKg; }
}
