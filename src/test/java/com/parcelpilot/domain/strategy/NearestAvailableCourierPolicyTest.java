package com.parcelpilot.domain.strategy;

import com.parcelpilot.domain.model.Courier;
import com.parcelpilot.domain.model.Customer;
import com.parcelpilot.domain.model.Delivery;
import com.parcelpilot.domain.model.DeliveryPriority;
import com.parcelpilot.domain.model.PackageSize;
import com.parcelpilot.domain.model.Parcel;
import com.parcelpilot.domain.model.VehicleType;
import com.parcelpilot.domain.model.value.Address;
import com.parcelpilot.domain.model.value.GeoPoint;
import com.parcelpilot.domain.model.value.Money;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NearestAvailableCourierPolicyTest {
    @Test
    void choosesNearestCourierThatCanCarryParcel() {
        var pickup = new GeoPoint(59.91, 10.75);
        var delivery = new Delivery(
                new Customer("Customer", "123"),
                new Address("Oslo", "A", "1", ""),
                new Address("Oslo", "B", "2", ""),
                pickup, new GeoPoint(59.92, 10.76),
                new Parcel(PackageSize.LARGE, 10, false),
                DeliveryPriority.STANDARD, Money.eur("10"), Clock.systemUTC()
        );
        var bicycle = new Courier("Bicycle", VehicleType.BICYCLE, new GeoPoint(59.9101, 10.7501));
        var van = new Courier("Van", VehicleType.VAN, new GeoPoint(59.911, 10.751));

        assertEquals(van, new NearestAvailableCourierPolicy().chooseCourier(delivery, List.of(bicycle, van)));
    }
}
