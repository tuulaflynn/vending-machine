package dao;

import model.ItemDto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    // The collection to store the data from the file. Due to encapsulation, all class variables should be private.
    // Class variables are stored in heap, objects of the class point to these class variables and to the instance of the object they have.
    private List<ItemDto> itemsCollection = new ArrayList<ItemDto>();

    @Override
    public List<ItemDto> readItemsFromFile() throws FileNotFoundException, IOException {
        // Objects needed to read from a file of characters.
        FileReader fr = new FileReader("items.txt");
        BufferedReader br = new BufferedReader(fr);

        String line = null;
        // An object is created from each line in the file.
        while ((line = br.readLine()) != null) {

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
        return fetchItems();        // Returning a copy of the collection.
    }

    @Override
    public List<ItemDto> fetchItems() {
        List<ItemDto> itemsCollectionCopy = new ArrayList<ItemDto>();

        // Return a collection copy through the layers so the original items within the collection cannot be edited
        // accidentally due to them having more than one reference pointing to them.
        for (ItemDto item : itemsCollection) {
            itemsCollectionCopy.add(item.copyItemObject());
        }
        return itemsCollectionCopy;
    }

    @Override
    public ItemDto fetchItem(String itemName) {
        // Returns the item if it exists in the collection, otherwise returns null.
        for (ItemDto item : itemsCollection) {
            if (item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean writeItemsToFile() throws FileNotFoundException {
        // Writes an object on each line to items.txt in CSV form.
        PrintWriter pw = new PrintWriter("items.txt");
        String line;
        for (ItemDto item : itemsCollection) {
            line = item.getItemId() + "," + item.getItemName() + "," + item.getItemCost() + "," + item.getItemStock();
            pw.println(line);
        }
        pw.flush();
        pw.close();
        return true;
    }

}
