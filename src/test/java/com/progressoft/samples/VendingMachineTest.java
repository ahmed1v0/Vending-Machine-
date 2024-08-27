package com.progressoft.samples;

public class VendingMachineTest {
    public static void main(String[] args) {
        Denominations initialDenominations = new Denominations(1.00, 1.00, 1.00, 1.00, 1.00, 5.00, 10.00);
        VendingMachine machine = new VendingMachine(initialDenominations);


        Money change = machine.purchaseItem(3.00, new Money(10.00, new Denominations(10.00)));

        System.out.println("Change returned: " + change); // Expected output: 7.00 Dinars
        System.out.println("Machine denominations after purchase: " + machine.getMachineDenominations());
    }
}
