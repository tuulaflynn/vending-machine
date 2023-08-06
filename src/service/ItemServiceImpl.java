package service;

import dao.ItemDao;
import dao.ItemDaoImpl;
import model.ItemDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    // create an object, itemDao, for which we can call methods of ItemDao on.
    // this means the service layer can call the dao layer.

    ItemDao itemDao;       // QUESTION: why would I not just initialised it here, i.e. have ItemDao itemDao = new ItemDaoImpl();
                                // not within the constructor. Not sure if there is a difference?

    public ItemServiceImpl() {
        itemDao = new ItemDaoImpl();
    }       // we only create one object of the dao class, as service class is only called once

    @Override
    public List<ItemDto> readItemsFromFile() throws FileNotFoundException, IOException {
        return itemDao.readItemsFromFile();
    }

    @Override
    public String displayItemsAndPrices() {     // QUESTION: is it fine to change the return type here? It was List<Object> for the method in the dao layer.
        List<Object> displayItemsAndPrices = itemDao.displayItemsAndPrices();       // Saving the returned result in the variable displayItemAndPrices.
        String displayItemsAndPricesString = "\n";

        for (int i = 0; i < (displayItemsAndPrices.size()); i = i + 2) {
            // As the data for an item spans two consecutive element in the collection, the increment expression is in steps of 2.
            displayItemsAndPricesString += displayItemsAndPrices.get(i) + " £" + displayItemsAndPrices.get(i+1) + "\n";
        }
        return displayItemsAndPricesString;
        // future edit for this method: make cost variable have 2dp i.e. 1.5 -> 1.50.
    }

    @Override
    public ItemDto fetchItem(String ItemName) {    // As itemNames are unique (otherwise their stock would be combined).
        return itemDao.fetchItem(ItemName);
    }

    @Override
    public boolean decreaseItemStock(int itemId) {
        return itemDao.decreaseItemStock(itemId);
    }

    @Override
    public boolean writeItemsToFile() {
        return itemDao.writeItemsToFile();      // as itemDao.writeItemsToFile return a boolean value.
    }
}
