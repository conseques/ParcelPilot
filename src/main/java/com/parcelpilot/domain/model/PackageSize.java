package com.parcelpilot.domain.model;

public enum PackageSize {
    SMALL(1.0),
    MEDIUM(5.0),
    LARGE(20.0);

    private final double maxWeightKg;

    PackageSize(double maxWeightKg) { this.maxWeightKg = maxWeightKg; }
    public double maxWeightKg() { return maxWeightKg; }
}
