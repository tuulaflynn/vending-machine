package service;

public class Change {
    public static String change(double changeDuePennies) {

        int quarters = 0; int dimes = 0; int nickels = 0; int pennies = 0;

        quarters = (int) (changeDuePennies / 25);
        double remainder = changeDuePennies % 25;
        dimes = (int) (remainder / 10);
        remainder = remainder % 10;
        nickels = (int) (remainder / 5);
        remainder = remainder % 5;
        pennies = (int) remainder;

        return (ChangeCoins.QUARTERS + ":" + quarters + " " + ChangeCoins.DIMES + ":" + dimes
        + " " + ChangeCoins.NICKELS + ":" + nickels + " " + ChangeCoins.PENNIES + ":" + pennies);
    }
}
