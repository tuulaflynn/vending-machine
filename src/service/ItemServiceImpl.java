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
    public ArrayList<Object> displayItemsAndPrices() {
        return itemDao.displayItemsAndPrices();
    }

    @Override
    public ItemDto fetchItem(int itemId) {
        return itemDao.fetchItem(itemId);
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
