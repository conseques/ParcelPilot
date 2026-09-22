package com.parcelpilot.application;

import com.parcelpilot.domain.model.Courier;
import com.parcelpilot.domain.model.Customer;
import com.parcelpilot.domain.model.Delivery;
import com.parcelpilot.domain.model.DeliveryPriority;
import com.parcelpilot.domain.model.DeliveryStatus;
import com.parcelpilot.domain.model.PackageSize;
import com.parcelpilot.domain.model.Parcel;
import com.parcelpilot.domain.model.VehicleType;
import com.parcelpilot.domain.model.value.Address;
import com.parcelpilot.domain.model.value.GeoPoint;
import com.parcelpilot.domain.port.CourierRepository;
import com.parcelpilot.domain.port.DeliveryRepository;
import com.parcelpilot.domain.service.CourierService;
import com.parcelpilot.domain.service.DeliveryRequest;
import com.parcelpilot.domain.service.DeliveryService;
import com.parcelpilot.domain.service.CustomerService;
import com.parcelpilot.domain.strategy.NearestAvailableCourierPolicy;
import com.parcelpilot.domain.strategy.StandardPricingStrategy;
import com.parcelpilot.infrastructure.repository.InMemoryCourierRepository;
import com.parcelpilot.infrastructure.repository.InMemoryCustomerRepository;
import com.parcelpilot.infrastructure.repository.InMemoryDeliveryRepository;

import java.time.Clock;

public final class ParcelPilotApplication {
    private ParcelPilotApplication() { }

    public static void main(String[] args) {
        var customerService = new CustomerService(new InMemoryCustomerRepository());
        CourierRepository courierRepository = new InMemoryCourierRepository();
        DeliveryRepository deliveryRepository = new InMemoryDeliveryRepository();
        var courierService = new CourierService(courierRepository);
        var deliveryService = new DeliveryService(
                deliveryRepository,
                courierRepository,
                new StandardPricingStrategy(),
                new NearestAvailableCourierPolicy(),
                Clock.systemUTC()
        );

        Customer customer = customerService.register(new Customer("Анна Петрова", "+47 555 01 02"));
        courierService.register(new Courier("Иван", VehicleType.BICYCLE, new GeoPoint(59.911, 10.752)));
        courierService.register(new Courier("Мария", VehicleType.VAN, new GeoPoint(59.920, 10.730)));

        Delivery delivery = deliveryService.create(new DeliveryRequest(
                customer,
                new Address("Oslo", "Karl Johans gate", "1", ""),
                new Address("Oslo", "Torggata", "12", "4"),
                new GeoPoint(59.913, 10.746),
                new GeoPoint(59.915, 10.752),
                new Parcel(PackageSize.SMALL, 0.8, true),
                DeliveryPriority.EXPRESS
        ));

        deliveryService.assignCourier(delivery.id());
        deliveryService.updateStatus(delivery.id(), DeliveryStatus.PICKED_UP);
        deliveryService.updateStatus(delivery.id(), DeliveryStatus.IN_TRANSIT);
        deliveryService.updateStatus(delivery.id(), DeliveryStatus.DELIVERED);

        Delivery completed = deliveryService.get(delivery.id());
        System.out.println("ParcelPilot demo");
        System.out.println("Delivery: " + completed.id());
        System.out.println("Route: " + completed.pickupAddress() + " -> " + completed.destination());
        System.out.println("Courier: " + completed.courier().name());
        System.out.println("Price: " + completed.price());
        System.out.println("Status: " + completed.status());
    }
}
