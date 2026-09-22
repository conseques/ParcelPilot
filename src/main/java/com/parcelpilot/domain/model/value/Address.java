package com.parcelpilot.domain.model.value;

import java.util.Objects;

public final class Address {
    private final String city;
    private final String street;
    private final String building;
    private final String apartment;

    public Address(String city, String street, String building, String apartment) {
        this.city = requireText(city, "City");
        this.street = requireText(street, "Street");
        this.building = requireText(building, "Building");
        this.apartment = apartment == null ? "" : apartment.trim();
    }

    public String city() { return city; }
    public String street() { return street; }
    public String building() { return building; }
    public String apartment() { return apartment; }

    @Override
    public String toString() {
        String suffix = apartment.isBlank() ? "" : ", apt. " + apartment;
        return city + ", " + street + " " + building + suffix;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Address address)) return false;
        return city.equals(address.city) && street.equals(address.street)
                && building.equals(address.building) && apartment.equals(address.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, building, apartment);
    }

    private static String requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be blank");
        }
        return value.trim();
    }
}
