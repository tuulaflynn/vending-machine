package exceptions;

public class NoItemInventoryException extends Exception{

    @Override
    public String getMessage() {
        return "Error this item has zero inventory. Please select only items from the vending machine menu.";
    }
}
