package dao;

import model.ItemDto;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao{

    // The collection to store the data from the file.
    List<ItemDto> itemsCollection = new ArrayList<ItemDto>();

    @Override
    public List<ItemDto> readItemsFromFile() throws FileNotFoundException, IOException {
        // QUESTION: here I had to change my return type from ArrayList to List as that is what I'd defined my collection to be
        // why do I need it to be a List rather than an ArrayList? I don't get what internally I am gaining but this step.

        // Objects needed to read from a file of characters
        FileReader fr = new FileReader("items.txt");        // QUESTION: why don't I use the relative path ../items.txt as the file isn't in the dao folder?
        BufferedReader br = new BufferedReader(fr);

        String line = null;
        while ((line = br.readLine()) != null) {             // For each line an object is created.

            // Obtaining the data for each property of ItemDto class from the line as the file is CVS.
            String[] lineArr = line.split(",");
            int itemId = Integer.parseInt(lineArr[0]);
            String itemName = lineArr[1];
            double itemCost = Double.parseDouble(lineArr[2]);
            int itemStock = Integer.parseInt(lineArr[3]);

            // Creating an object from the line and adding it to the collection.
            ItemDto itemDto = new ItemDto(itemId, itemName, itemCost, itemStock);
            itemsCollection.add(itemDto);

        }

        // Return a collection copy through the layers so the original items within the collection cannot be edited
        // accidentally due to them having more than one reference pointing to them.
        // COME BACK TO THIS TOMORROW, RIGHT NOW I AM SENDING THE WHOLE ORIGINAL COLLECTION WHICH I CAN'T DO BUT THE METHOD
        // I HAVE IS ONLY FOR COPYING INDIVIDUAL OBJECTS.
        return itemsCollection;
    }

    @Override
    public ArrayList<Object> displayItemsAndPrices() {
        System.out.println("display items and prices");
        return null;
    }

    @Override
    public ItemDto fetchItem(int itemId) {
        return null;
    }

    @Override
    public boolean decreaseItemStock(int itemId) {
        return false;
    }

    @Override
    public boolean writeItemsToFile() {
        return false;
    }
}
