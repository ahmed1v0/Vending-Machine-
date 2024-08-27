package com.progressoft.samples;

import java.util.*;

public class Denominations {
    private final Map<Double, Integer> denominations = new TreeMap<>(Collections.reverseOrder());

    public Denominations(double... vals) {
        for (double val : vals) {
            denominations.put(val, denominations.getOrDefault(val, 0) + 1);
        }
    }

    // Add denominations from another Denominations instance
    public void add(Denominations other) {
        for (Map.Entry<Double, Integer> entry : other.denominations.entrySet()) {
            double denomination = entry.getKey();
            int count = entry.getValue();
            denominations.put(denomination, denominations.getOrDefault(denomination, 0) + count);
        }
    }

    // Multiply denominations by a count
    public Denominations multiply(int count) {
        Map<Double, Integer> newDenominations = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Double, Integer> entry : this.denominations.entrySet()) {
            double denomination = entry.getKey();
            int existingCount = entry.getValue();
            newDenominations.put(denomination, existingCount * count);
        }
        return new Denominations(newDenominations);
    }

    // Check if it's possible to subtract another Denominations instance
    public boolean canSubtract(Denominations other) {
        Map<Double, Integer> tempDenominations = new TreeMap<>(Collections.reverseOrder());
        tempDenominations.putAll(this.denominations);

        for (Map.Entry<Double, Integer> entry : other.denominations.entrySet()) {
            double denomination = entry.getKey();
            int requiredCount = entry.getValue();

            for (Map.Entry<Double, Integer> itr : tempDenominations.entrySet()) {
                double currentDenomination = itr.getKey();
                if (currentDenomination <= denomination) {
                    int availableCount = itr.getValue();
                    int neededCount = (int) Math.ceil((double) requiredCount / (denomination / currentDenomination));
                    if (availableCount >= neededCount) {
                        requiredCount -= neededCount * (denomination / currentDenomination);
                        tempDenominations.put(currentDenomination, availableCount - neededCount);
                        if (requiredCount <= 0) break;
                    }
                }
            }

            if (requiredCount > 0) return false;
        }

        return true;
    }

    // Subtract another Denominations instance
    public Denominations subtract(Denominations other) {
        if (!canSubtract(other)) {
            throw new IllegalArgumentException("Insufficient denominations to subtract.");
        }

        Map<Double, Integer> result = new TreeMap<>(Collections.reverseOrder());
        result.putAll(this.denominations);

        for (Map.Entry<Double, Integer> entry : other.denominations.entrySet()) {
            double denomination = entry.getKey();
            int requiredCount = entry.getValue();

            for (Map.Entry<Double, Integer> itr : result.entrySet()) {
                double currentDenomination = itr.getKey();
                if (currentDenomination <= denomination) {
                    int availableCount = itr.getValue();
                    int neededCount = (int) Math.ceil((double) requiredCount / (denomination / currentDenomination));
                    int subtractCount = Math.min(availableCount, neededCount);
                    requiredCount -= subtractCount * (denomination / currentDenomination);
                    result.put(currentDenomination, availableCount - subtractCount);
                    if (requiredCount <= 0) break;
                }
            }

            if (requiredCount > 0) {
                throw new IllegalArgumentException("Cannot subtract " + other + ": insufficient denominations.");
            }
        }

        return new Denominations(result);
    }

    // Constructor for creating Denominations from a Map
    private Denominations(Map<Double, Integer> denominations) {
        this.denominations.putAll(denominations);
    }

    @Override
    public String toString() {
        return denominations.toString();
    }

    // Method to calculate the change
    public static ChangeResult calculateChange(Denominations machineDenominations, double payment, double cost) {
        double changeAmount = payment - cost;
        if (changeAmount < 0) {
            throw new IllegalArgumentException("Insufficient payment.");
        }

        Map<Double, Integer> changeDenominations = new TreeMap<>(Collections.reverseOrder());
        Map<Double, Integer> remainingMachineDenominations = new TreeMap<>(Collections.reverseOrder());
        remainingMachineDenominations.putAll(machineDenominations.denominations);

        for (double denom : remainingMachineDenominations.keySet()) {
            int count = (int) (changeAmount / denom);
            if (count > 0) {
                int availableCount = remainingMachineDenominations.get(denom);
                int toGive = Math.min(count, availableCount);
                if (toGive > 0) {
                    changeDenominations.put(denom, toGive);
                    changeAmount -= toGive * denom;
                    remainingMachineDenominations.put(denom, availableCount - toGive);
                }
            }
            if (changeAmount <= 0) break;
        }

        if (changeAmount > 0) {
            throw new IllegalArgumentException("Cannot give exact change with available denominations.");
        }

        return new ChangeResult(changeAmount, changeDenominations, remainingMachineDenominations);
    }

    public static class ChangeResult {
        public final double changeAmount;
        public final Map<Double, Integer> changeDenominations;
        public final Map<Double, Integer> remainingMachineDenominations;

        public ChangeResult(double changeAmount, Map<Double, Integer> changeDenominations, Map<Double, Integer> remainingMachineDenominations) {
            this.changeAmount = changeAmount;
            this.changeDenominations = changeDenominations;
            this.remainingMachineDenominations = remainingMachineDenominations;
        }

        @Override
        public String toString() {
            return String.format("Change returned: %.2f\nChange denominations: %s\nMachine denominations after purchase: %s",
                    changeAmount, changeDenominations, remainingMachineDenominations);
        }
    }
}
