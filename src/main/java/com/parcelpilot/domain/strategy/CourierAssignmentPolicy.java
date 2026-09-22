package com.parcelpilot.domain.strategy;

import com.parcelpilot.domain.model.Courier;
import com.parcelpilot.domain.model.Delivery;

import java.util.List;

public interface CourierAssignmentPolicy {
    Courier chooseCourier(Delivery delivery, List<Courier> couriers);
}
