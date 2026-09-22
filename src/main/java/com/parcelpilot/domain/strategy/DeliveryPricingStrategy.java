package com.parcelpilot.domain.strategy;

import com.parcelpilot.domain.model.DeliveryPriority;
import com.parcelpilot.domain.model.Parcel;
import com.parcelpilot.domain.model.value.Money;

public interface DeliveryPricingStrategy {
    Money calculate(Parcel parcel, double distance, DeliveryPriority priority);
}
