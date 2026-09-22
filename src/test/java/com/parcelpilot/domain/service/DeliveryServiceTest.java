package com.parcelpilot.domain.service;

import com.parcelpilot.domain.model.Courier;
import com.parcelpilot.domain.model.Customer;
import com.parcelpilot.domain.model.DeliveryPriority;
import com.parcelpilot.domain.model.DeliveryStatus;
import com.parcelpilot.domain.model.PackageSize;
import com.parcelpilot.domain.model.Parcel;
import com.parcelpilot.domain.model.VehicleType;
import com.parcelpilot.domain.model.value.Address;
import com.parcelpilot.domain.model.value.GeoPoint;
import com.parcelpilot.domain.port.CourierRepository;
import com.parcelpilot.domain.port.DeliveryRepository;
import com.parcelpilot.domain.strategy.NearestAvailableCourierPolicy;
import com.parcelpilot.domain.strategy.StandardPricingStrategy;
import com.parcelpilot.infrastructure.repository.InMemoryCourierRepository;
import com.parcelpilot.infrastructure.repository.InMemoryDeliveryRepository;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryServiceTest {
    @Test
    void releasesCourierAfterSuccessfulDelivery() {
        CourierRepository courierRepository = new InMemoryCourierRepository();
        DeliveryRepository deliveryRepository = new InMemoryDeliveryRepository();
        var courier = new Courier("Courier", VehicleType.VAN, new GeoPoint(59.91, 10.75));
        courierRepository.save(courier);
        var service = new DeliveryService(
                deliveryRepository, courierRepository,
                new StandardPricingStrategy(), new NearestAvailableCourierPolicy(),
                Clock.fixed(Instant.EPOCH, ZoneOffset.UTC)
        );

        var delivery = service.create(new DeliveryRequest(
                new Customer("Customer", "123"),
                new Address("Oslo", "A", "1", ""), new Address("Oslo", "B", "2", ""),
                new GeoPoint(59.91, 10.75), new GeoPoint(59.92, 10.76),
                new Parcel(PackageSize.SMALL, 0.5, false), DeliveryPriority.STANDARD
        ));
        service.assignCourier(delivery.id());
        assertFalse(courier.isAvailable());

        service.updateStatus(delivery.id(), DeliveryStatus.PICKED_UP);
        service.updateStatus(delivery.id(), DeliveryStatus.IN_TRANSIT);
        service.updateStatus(delivery.id(), DeliveryStatus.DELIVERED);

        assertTrue(courier.isAvailable());
    }
}
