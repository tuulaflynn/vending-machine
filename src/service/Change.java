package service;

public class Change {
    public static String change(double changeDuePennies) {
        // Method calculates the change in max quarters, max dimes, max nickles and min pennies.
        // Argument monetary amount is given in pennies.

        int quarters = 0; int dimes = 0; int nickels = 0; int pennies = 0;

        quarters = (int) (changeDuePennies / 25);       // Integer parsing takes the floor of the division as desired.
        double remainder = changeDuePennies % 25;       // Removing quarters which were calculated in the step previous.
        dimes = (int) (remainder / 10);                 // int dividing the remainder to calculate dimes needed.
        remainder = remainder % 10;                     // Removing the dimes from the remainder and updating it.
        nickels = (int) (remainder / 5);                // int division to calculate max number of nickels.
        remainder = remainder % 5;                      // Removing nickles calculated from the remainder.
        pennies = (int) remainder;                      // Remainder change left is pennies.

        return (ChangeCoins.QUARTERS + ":" + quarters + " " + ChangeCoins.DIMES + ":" + dimes
        + " " + ChangeCoins.NICKELS + ":" + nickels + " " + ChangeCoins.PENNIES + ":" + pennies);
    }
}
