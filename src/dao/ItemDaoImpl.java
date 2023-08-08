package dao;

import model.ItemDto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao{
    // QUESTION: should class variables be private? e.g. lines 16 and 17?


    // The collection to store the data from the file.
    List<ItemDto> itemsCollection = new ArrayList<ItemDto>();
    List<ItemDto> itemsCollectionCopy = new ArrayList<ItemDto>();       // QUESTION: is it correct to create this as a class variable?
    // I could create it inside fetchItems() but instead I chose to create it once and clear it every time fetchItems()
    // is used. Is there a preferred way? I feel like I am filtering through the collection a lot, i.e. in this layer
    // and then in the service layer but this also seems like this way it would be easier to update in the future.


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

        // List<ItemDto> itemsCollectionCopy = new ArrayList<ItemDto>();        this is now a class variable

        // QUESTION: Here I could create a dto object and call fetchItems method on it to achieve itemsCollectionCopy.
        // Creating an object feels a bit unnecessary but the code traversing through the collection is repeated in fetchItems(). What is the better way?
        // Should I make fetchItems static?
        for (int i = 0; i < itemsCollection.size(); i++) {
            // Each object in the collection gets copied, then added to the return collection, itemsCollectionCopy.
            itemsCollectionCopy.add(itemsCollection.get(i).copyItemObject());
        }
        return itemsCollectionCopy;
    }


    @Override
    public List<ItemDto> fetchItems() {
        // The collection must be cleared when the method is called, so items will not be duplicated in the collection
        itemsCollectionCopy.clear();

        // Return a collection copy through the layers so the original items within the collection cannot be edited
        // accidentally due to them having more than one reference pointing to them.
        itemsCollectionCopy.addAll(itemsCollection);
        return itemsCollectionCopy;
    }

    @Override
    public ItemDto fetchItem(String itemName) {
        for(ItemDto item: itemsCollection) {
            if(item.getItemName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean writeItemsToFile() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("items.txt");
        String line;
        for (ItemDto item: itemsCollection) {
            line = item.getItemId() + "," + item.getItemName() + "," + item.getItemCost() + "," + item.getItemStock();
            pw.println(line);
        }
        pw.flush();
        pw.close();
        return true;
    }
}
