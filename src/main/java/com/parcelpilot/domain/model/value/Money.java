package com.parcelpilot.domain.model.value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private final BigDecimal amount;
    private final String currency;

    public Money(BigDecimal amount, String currency) {
        Objects.requireNonNull(amount, "Amount must not be null");
        if (amount.signum() < 0) throw new IllegalArgumentException("Amount must not be negative");
        if (currency == null || currency.isBlank()) throw new IllegalArgumentException("Currency must not be blank");
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.currency = currency.toUpperCase();
    }

    public static Money eur(String amount) {
        return new Money(new BigDecimal(amount), "EUR");
    }

    public static Money eur(double amount) {
        return eur(String.valueOf(amount));
    }

    public BigDecimal amount() { return amount; }
    public String currency() { return currency; }

    public Money add(Money other) {
        ensureSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money multiply(double multiplier) {
        if (multiplier < 0) throw new IllegalArgumentException("Multiplier must not be negative");
        return new Money(amount.multiply(BigDecimal.valueOf(multiplier)), currency);
    }

    @Override
    public int compareTo(Money other) {
        ensureSameCurrency(other);
        return amount.compareTo(other.amount);
    }

    @Override
    public String toString() { return amount + " " + currency; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Money money)) return false;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() { return Objects.hash(amount, currency); }

    private void ensureSameCurrency(Money other) {
        if (!currency.equals(other.currency)) throw new IllegalArgumentException("Currencies must match");
    }
}
