package service;
// Used chatGPT to help with this enum class as I am unsure.
public enum ChangeCoins {
    QUARTERS (0.25), DIMES(0.1), NICKELS(0.5), PENNIES(0.01);

    private final double monetaryValue;

    ChangeCoins(double monetaryValue) {
        this.monetaryValue = monetaryValue;
        // this indicates one of the constants, i.e.
    }

    public double getValue() {
        return monetaryValue;
    }
}
