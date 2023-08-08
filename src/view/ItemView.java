package view;

import exceptions.NoItemInventoryException;
import model.ItemDto;
import service.ItemService;
import service.ItemServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class ItemView {
    // Create one service object to interact (send messages) (use methods) with the service layer from the view layer.
    // Use polymorphism so the object can only access interface methods (not internal methods of the implementation class).
    ItemService itemService = new ItemServiceImpl();

    // Object to format correct output (2 d.p) for English currency.
    Formatter formatter = new Formatter();

    public void readItemsFromFile() {           // QUESTION: is it bad to use the same method name but change the return type? As I don't see a need for it to return anything
        try {
            itemService.readItemsFromFile();

        } catch (FileNotFoundException e) {
            System.out.println("Error. There has been a problem trying to find the items.txt file.");
        } catch (IOException io) {
            System.out.println("Error. There has been an issue reading from items.txt file.");
        }
    }

    public void homeScreen() {
        String userChoice;
        Scanner scan = new Scanner(System.in);

        do {
            List<ItemDto> itemsCollection = itemService.fetchItemsNonZeroStock();
            Formatter formattedCost;        // To display prices to 2 d.p.
            // QUESTION: is it best to have the Formatter type outside the for loop ? Or does it not matter as I could have it on line 42.

            System.out.println("****************************************************");
            System.out.println("VENDING MACHINE");

            // Traverses through itemsCollection to output itemName and itemPrice (formatted to 2 d.p.) on each line
            for (ItemDto item : itemsCollection) {
                formatter = new Formatter();        // Reassigns formatter, so it does not hold any information for another item.
                formattedCost = formatter.format("%.2f", item.getItemCost());       // Formats current item's cost using formatter object.
                System.out.println(item.getItemName() + "  £" + formattedCost);
            }

            System.out.println("-----------------------------------------------------");
            System.out.println("Select an option ");
            System.out.println("1. Vend an item");
            System.out.println("2. Exit");
            System.out.println("-----------------------------------------------------");

            // Scan for user choice which is inputted as a string
            userChoice = scan.next();
            scan.nextLine();  // Scans to the end of the line, so for next scan the scanner starts at the start of the line.
            System.out.println("****************************************************");

            if (userChoice.equals("1")) {
                System.out.println("Enter money (numerical values only): ");
                Double userMoney = scan.nextDouble();       // could add a try except for if the user enter a non-numeric value.
                scan.nextLine();        // To correctly take the scanner to the end of the entered line.

                System.out.println("Enter the name of the item you want to vend: ");
                String name = scan.nextLine();      // need to account for if there is a space

                try {
                    ItemDto itemNamed = itemService.fetchItem(name);

                    if (itemNamed != null) {
                        String vendPossible = itemService.checkAndVendIfPossible(userMoney, itemNamed);
                        if (vendPossible != null) {
                            System.out.println("Item has been vended");
                            System.out.println("Your change is...");
                            System.out.println(vendPossible);

                        } else {
                            System.out.println("Insufficient funds");
                            formatter = new Formatter();
                            System.out.println("Money returned: £" + formatter.format("%.2f", userMoney));
                        }
                    } else {
                        System.out.println("There is no item in the vending machine with name '" + name + "'.");
                        System.out.println("Names are case sensitive, use the name given in the menu. Your money has been refunded. Returning to menu...");
                    }
                } catch (NoItemInventoryException e1) {
                    System.out.println(e1.getMessage());
                }

            } else if (userChoice.equals("2")) {
                try {
                    itemService.writeItemsToFile();
                } catch (FileNotFoundException e1) {
                    System.out.println("Error. The file 'items.txt' to write to cannot be found. File should be located in src folder.");
                }
                System.out.println("Exiting program. Vending machine files have been updated. ");
                // QUESTION:
                // break; is unnecessary as the condition will fail on the next while loop anyway.

            } else {
                System.out.println("Invalid entry. Please select an option by entering only the number.");
            }
        } while (!userChoice.equals("2"));
    }


}
