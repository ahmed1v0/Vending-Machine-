package com.progressoft.samples;

public class VendingMachine {
    private Denominations machineDenominations;

    public VendingMachine(Denominations initialDenominations) {
        this.machineDenominations = initialDenominations;
    }

    public Money purchaseItem(double itemPrice, Money insertedMoney) {
        Money cost = new Money(itemPrice, new Denominations());

        if (insertedMoney.amount() < itemPrice) {
            throw new IllegalArgumentException("Insufficient funds provided.");
        }

        Money change = insertedMoney.minus(cost);

        if (!machineDenominations.canSubtract(change.getDenominations())) {
            throw new IllegalStateException("Cannot return the required change.");
        }

        machineDenominations = machineDenominations.subtract(change.getDenominations());

        machineDenominations.add(insertedMoney.getDenominations());

        return change;
    }

    public Denominations getMachineDenominations() {
        return machineDenominations;
    }
}

