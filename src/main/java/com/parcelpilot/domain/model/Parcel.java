package com.parcelpilot.domain.model;

import java.util.Objects;
import java.util.UUID;

public final class Parcel {
    private final UUID id;
    private final PackageSize size;
    private final double weightKg;
    private final boolean fragile;

    public Parcel(PackageSize size, double weightKg, boolean fragile) {
        this.id = UUID.randomUUID();
        this.size = Objects.requireNonNull(size);
        if (weightKg <= 0 || weightKg > size.maxWeightKg()) {
            throw new IllegalArgumentException("Weight must fit selected package size");
        }
        this.weightKg = weightKg;
        this.fragile = fragile;
    }

    public UUID id() { return id; }
    public PackageSize size() { return size; }
    public double weightKg() { return weightKg; }
    public boolean isFragile() { return fragile; }
}
