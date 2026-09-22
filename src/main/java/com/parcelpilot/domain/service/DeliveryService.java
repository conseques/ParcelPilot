package com.parcelpilot.domain.service;

import com.parcelpilot.domain.exception.EntityNotFoundException;
import com.parcelpilot.domain.model.Courier;
import com.parcelpilot.domain.model.Delivery;
import com.parcelpilot.domain.model.DeliveryStatus;
import com.parcelpilot.domain.port.CourierRepository;
import com.parcelpilot.domain.port.DeliveryRepository;
import com.parcelpilot.domain.strategy.CourierAssignmentPolicy;
import com.parcelpilot.domain.strategy.DeliveryPricingStrategy;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

public final class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CourierRepository courierRepository;
    private final DeliveryPricingStrategy pricingStrategy;
    private final CourierAssignmentPolicy assignmentPolicy;
    private final Clock clock;

    public DeliveryService(DeliveryRepository deliveryRepository,
                            CourierRepository courierRepository,
                            DeliveryPricingStrategy pricingStrategy,
                            CourierAssignmentPolicy assignmentPolicy,
                            Clock clock) {
        this.deliveryRepository = deliveryRepository;
        this.courierRepository = courierRepository;
        this.pricingStrategy = pricingStrategy;
        this.assignmentPolicy = assignmentPolicy;
        this.clock = clock;
    }

    public Delivery create(DeliveryRequest request) {
        double distance = request.pickupPoint().distanceTo(request.destinationPoint());
        Delivery delivery = new Delivery(
                request.customer(),
                request.pickupAddress(),
                request.destination(),
                request.pickupPoint(),
                request.destinationPoint(),
                request.parcel(),
                request.priority(),
                pricingStrategy.calculate(request.parcel(), distance, request.priority()),
                clock
        );
        return deliveryRepository.save(delivery);
    }

    public Delivery assignCourier(UUID deliveryId) {
        Delivery delivery = get(deliveryId);
        Courier courier = assignmentPolicy.chooseCourier(delivery, courierRepository.findAll());
        delivery.assignTo(courier);
        courier.markBusy();
        courierRepository.save(courier);
        return deliveryRepository.save(delivery);
    }

    public Delivery updateStatus(UUID deliveryId, DeliveryStatus status) {
        Delivery delivery = get(deliveryId);
        delivery.moveTo(status);
        if (status == DeliveryStatus.DELIVERED && delivery.courier() != null) {
            delivery.courier().moveTo(delivery.destinationPoint());
            delivery.courier().markAvailable();
            courierRepository.save(delivery.courier());
        } else if (status == DeliveryStatus.CANCELLED && delivery.courier() != null) {
            delivery.courier().markAvailable();
            courierRepository.save(delivery.courier());
        }
        return deliveryRepository.save(delivery);
    }

    public Delivery get(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found: " + deliveryId));
    }

    public List<Delivery> findAll() { return deliveryRepository.findAll(); }
}
