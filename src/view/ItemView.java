package view;

import model.ItemDto;
import service.ItemService;
import service.ItemServiceImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

// Dealing with exceptions in this layer

public class ItemView {
    // We create one service object to interact (send messages) (use methods) with the service layer from the view layer.
    // Again we use polymorphism so the object can only access interface methods (not internal methods of the implementation class).
    ItemService itemService = new ItemServiceImpl();
    // Object to format correct output (2 d.p) for English currency.
    Formatter formatter = new Formatter();
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
            Formatter formattedCost;        // QUESTION: is it best to have the Formatter type outside the for loop ? Or does it not matter as I could have it on line 46.

            for (ItemDto item: itemsCollection) {
                formattedCost = formatter.format("%.2f", item.getItemCost());       // formats current item's cost using formatter object
                displayNameAndPrice += item.getItemName() + "  £" + formattedCost + "\n";
                formatter = new Formatter();        // reassigns formatter so it does not hold any information of the previous item.
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
                    String vendPossible = itemService.checkAndVendIfPossible(userMoney, itemNamed);
                    if (vendPossible != null) {
                        System.out.println("Item has been vended");
                        System.out.println("Your change is...");
                        System.out.println(vendPossible);

                    }
                    else {
                        System.out.println("Insufficient funds");
                        System.out.println("Money returned: £" + formatter.format("%.2f", userMoney));
                    }
                }
                else {
                    System.out.println("There is no item in the vending machine with name '" + name + "'.");
                    System.out.println("Names are case sensitive, use the name given in the menu. Your money has been refunded. Returning to menu...");
                }

            }
            else if (userChoice.equals("2")) { // QUESTION:
                // break; is unnecessary as the condition will fail on the next while loop anyway.
            }
            else {
                System.out.println("Invalid entry. Please select an option by entering only the number.");
            }
        } while (!userChoice.equals("2"));
    }


}
