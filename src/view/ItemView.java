package view;

import model.ItemDto;
import service.Change;
import service.ItemService;
import service.ItemServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Dealing with exceptions in this layer

public class ItemView {
    // We create one service object to interact (send messages) (use methods) with the service layer from the view layer.
    // Again we use polymorphism so the object can only access interface methods (not internal methods of the implementation class).
    ItemService itemService = new ItemServiceImpl();
    public void readItemsFromFile() {           // is it bad to use the same method name but change the return type? As I don't see a need for it to return anything
        try {
            itemService.readItemsFromFile();

        } catch(FileNotFoundException e) {
            System.out.println("Error. There has been a problem trying to find the items.txt file.");
        }
        catch (IOException io) {
            System.out.println("Error. There has been an issue reading from items.txt file.");
        }
    }

    public void homeScreen() {
        String userChoice = null;
        Scanner scan = new Scanner(System.in);

        do {
            System.out.println("****************************************************");
            System.out.println("VENDING MACHINE");
            List<ItemDto> itemsCollection = itemService.fetchItemsNonZeroStock();
            String displayNameAndPrice = "\n";
            for (ItemDto item: itemsCollection) {
                displayNameAndPrice += item.getItemName() + "  £" + item.getItemCost() + "\n";
            }
            System.out.println(displayNameAndPrice);
            System.out.println("-----------------------------------------------------");
            System.out.println("Select an option ");
            System.out.println("1. Vend an item");
            System.out.println("2. Exit");
            System.out.println("-----------------------------------------------------");
            userChoice = scan.next();
            scan.nextLine();        // To scan to the end of the line so next time scanner is used it starts at the desired position.
            System.out.println("****************************************************");


            if (userChoice.equals("1")) {
                System.out.println("Enter money (numerical values only): ");
                Double userMoney = scan.nextDouble();       // could add a try except for if the user enter a non-numeric value.
                scan.nextLine();        // To correctly take the scanner to the end of the entered line.

                System.out.println("Enter the name of the item you want to vend: ");
                String name = scan.nextLine();      // need to account for if there is a space

                ItemDto itemNamed = itemService.fetchItem(name);
                if (itemNamed != null) {
                    boolean vendPossible = itemService.checkVendPossible(userMoney, itemNamed);
                    if (vendPossible) {  // == true not needed in the condition
                        System.out.println("Item has been vended");
                        System.out.println("Your change is...");
                        double changeDuePennies = (userMoney - itemNamed.getItemCost()) * 100;     // QUESTION: I could do and call these two lines in service (within the checkVendPossible method)
                        System.out.println(Change.change(changeDuePennies));

                    }
                    else {
                        System.out.println("Insufficient funds");
                        System.out.println("Money returned: £" + userMoney);
                    }
                }
                else {
                    System.out.println("There is no item in the vending machine with name '" + name + "'.");
                    System.out.println("Names are case sensitive, use the name given in the menu. Your money has been refunded. Returning to menu...");
                }

            }
            else if (userChoice.equals("2")) {
                // Maybe should code this better as the condition is catching this condition but this condition is needed for user input validation
            }
            else {
                System.out.println("Invalid entry. Please select an option by entering only the number.");
            }
        } while (!userChoice.equals("2"));
    }


}
