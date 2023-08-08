package service;
// Used chatGPT to help with this enum class as I am unsure on enums.
public enum ChangeCoins {
    // These constants are attached (like methods) to ChangeCoins class.
    // They can be called like a static method 'ChangeCoins.QUARTERS' and when they are called (initialised)
    // the private constructor is run. Which we have set so the value in the bracket of the constant is
    // associated to ... one of the constants?
    QUARTERS (0.25),
    DIMES(0.1),
    NICKELS(0.5),
    PENNIES(0.01);

    // We don't need the constructor or method or the bracketed value of the coins, as we don't use them in the program.
    // I have left them in for future understanding of enums.
    private final double monetaryValue;

    ChangeCoins(double monetaryValue) {
        // The constructor is private  as no access code is given hence it has the default which is package level only.
        this.monetaryValue = monetaryValue;

    }

    public double getValue() {
        return monetaryValue;
    }
}
