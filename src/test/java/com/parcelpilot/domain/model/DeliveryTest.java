package com.parcelpilot.domain.model;

import com.parcelpilot.domain.exception.InvalidDeliveryStateException;
import com.parcelpilot.domain.model.value.Address;
import com.parcelpilot.domain.model.value.GeoPoint;
import com.parcelpilot.domain.model.value.Money;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryTest {
    private final Clock clock = Clock.fixed(Instant.parse("2026-01-01T10:00:00Z"), ZoneOffset.UTC);

    @Test
    void followsHappyPathFromCreatedToDelivered() {
        Delivery delivery = newDelivery();
        Courier courier = new Courier("Alex", VehicleType.SCOOTER, new GeoPoint(59.91, 10.75));

        delivery.assignTo(courier);
        delivery.moveTo(DeliveryStatus.PICKED_UP);
        delivery.moveTo(DeliveryStatus.IN_TRANSIT);
        delivery.moveTo(DeliveryStatus.DELIVERED);

        assertEquals(DeliveryStatus.DELIVERED, delivery.status());
    }

    @Test
    void rejectsSkippingRequiredState() {
        Delivery delivery = newDelivery();

        assertThrows(InvalidDeliveryStateException.class,
                () -> delivery.moveTo(DeliveryStatus.IN_TRANSIT));
    }

    private Delivery newDelivery() {
        Customer customer = new Customer("Test customer", "+1 555 0100");
        Address address = new Address("Oslo", "Main street", "1", "");
        return new Delivery(
                customer, address, address,
                new GeoPoint(59.91, 10.75), new GeoPoint(59.92, 10.76),
                new Parcel(PackageSize.SMALL, 0.5, false),
                DeliveryPriority.STANDARD, Money.eur("10"), clock
        );
    }
}
