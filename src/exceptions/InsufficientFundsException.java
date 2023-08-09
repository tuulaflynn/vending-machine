package exceptions;

public class InsufficientFundsException {
    // I don't want to throw this exception unnecessary as my code can check for whether it occurred by a conditional
    // statement, I thought it was bad to throw anything the code could account for?
    //
    // -> Answer
    // The reasons to have a throw case is so it can be logged as an anomoly by the system. Future edit to create this exception as a throw

}
