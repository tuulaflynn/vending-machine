package view;

import model.ItemDto;
import service.ItemService;
import service.ItemServiceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
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
            System.out.println(itemService.displayItemsAndPrices());
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
                System.out.println(userMoney);

                System.out.println("Enter the name of the item you want to vend: ");
                String name = scan.nextLine();      // need to account for if there is a space

                ItemDto itemNamed = itemService.fetchItem(name);
                if (itemNamed != null) {
                    System.out.println(itemNamed);
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
