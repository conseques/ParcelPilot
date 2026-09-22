package com.parcelpilot.domain.strategy;

import com.parcelpilot.domain.model.DeliveryPriority;
import com.parcelpilot.domain.model.Parcel;
import com.parcelpilot.domain.model.value.Money;

public final class StandardPricingStrategy implements DeliveryPricingStrategy {
    private static final double BASE_PRICE = 4.50;
    private static final double PRICE_PER_KILOMETER = 1.20;

    @Override
    public Money calculate(Parcel parcel, double distance, DeliveryPriority priority) {
        double price = BASE_PRICE + distance * PRICE_PER_KILOMETER;
        price *= priorityMultiplier(priority);
        if (parcel.isFragile()) price += 3.00;
        return Money.eur(price);
    }

    private double priorityMultiplier(DeliveryPriority priority) {
        return switch (priority) {
            case STANDARD -> 1.0;
            case EXPRESS -> 1.35;
            case SAME_DAY -> 1.80;
        };
    }
}
