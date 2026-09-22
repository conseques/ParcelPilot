package com.parcelpilot.domain.model;

public enum DeliveryPriority {
    STANDARD(1),
    EXPRESS(2),
    SAME_DAY(3);

    private final int level;

    DeliveryPriority(int level) { this.level = level; }
    public int level() { return level; }
}
