package com.progressoft.samples;

public class Money {
    public static final Money Zero = new Money(0.00, new Denominations(0.00));
    public static final Money OnePiaster = new Money(0.01, new Denominations(0.01));
    public static final Money FivePiasters = new Money(0.05, new Denominations(0.05));
    public static final Money TenPiasters = new Money(0.10, new Denominations(0.10));
    public static final Money TwentyFivePiasters = new Money(0.25, new Denominations(0.25));
    public static final Money FiftyPiasters = new Money(0.50, new Denominations(0.50));
    public static final Money OneDinar = new Money(1.00, new Denominations(1.00));
    public static final Money FiveDinars = new Money(5.00, new Denominations(5.00));
    public static final Money TenDinars = new Money(10.00, new Denominations(10.00));
    public static final Money TwentyDinars = new Money(20.00, new Denominations(20.00));
    public static final Money FiftyDinars = new Money(50.00, new Denominations(50.00));

    private final double amount;
    private final Denominations denominations;

    public Money(double amount, Denominations denominations) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
        this.denominations = denominations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Math.abs(money.amount - amount) < 1e-6;
    }

    public double amount() {
        return this.amount;
    }

    public Denominations getDenominations() {
        return this.denominations;
    }

    public Money times(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }

        double newAmount = this.amount * count;
        Denominations newDenominations = this.denominations.multiply(count);
        return new Money(newAmount, newDenominations);
    }

    public static Money sum(Money... items) {
        double total = 0.00;
        Denominations totalDenominations = new Denominations();
        for (Money item : items) {
            total += item.amount();
            totalDenominations.add(item.getDenominations());
        }
        return new Money(total, totalDenominations);
    }

    public Money plus(Money other) {
        this.denominations.add(other.getDenominations());
        return new Money(this.amount + other.amount(), this.denominations);
    }

    public Money minus(Money other) {
        if (this.amount < other.amount() - 1e-6) {
            throw new IllegalArgumentException("Cannot subtract " + other.amount() + " from " + this.amount + ": insufficient funds.");
        }

        if (!this.denominations.canSubtract(other.getDenominations())) {
            throw new IllegalArgumentException("Cannot subtract due to insufficient denominations.");
        }

        double newAmount = this.amount - other.amount();
        Denominations newDenominations = this.denominations.subtract(other.getDenominations());
        return new Money(newAmount, newDenominations);
    }

    @Override
    public String toString() {
        return String.format("%.2f", amount);
    }
}
