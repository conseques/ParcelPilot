package com.parcelpilot.domain.strategy;

import com.parcelpilot.domain.model.DeliveryPriority;
import com.parcelpilot.domain.model.PackageSize;
import com.parcelpilot.domain.model.Parcel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardPricingStrategyTest {
    private final StandardPricingStrategy strategy = new StandardPricingStrategy();

    @Test
    void addsFragileFeeAndPriorityMultiplier() {
        Parcel parcel = new Parcel(PackageSize.SMALL, 0.5, true);

        var price = strategy.calculate(parcel, 10, DeliveryPriority.EXPRESS);

        assertEquals("25.28 EUR", price.toString());
    }
}
