package service;

import dao.ItemDao;
import dao.ItemDaoImpl;
import exceptions.NoItemInventoryException;
import model.ItemDto;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
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
    public List<ItemDto> fetchItemsNonZeroStock() {
        List<ItemDto> itemsCollection = itemDao.fetchItems();

        Iterator<ItemDto> iterator = itemsCollection.iterator();
        // Creating an iterator for the collection itemsCollection, it currently points to before the first element.
        while (iterator.hasNext()) {
            ItemDto item = iterator.next();
            // iterator.next() returns the next element in the collection and moves the iterator to the next position.
            // As the iterator initially points to before the first item, it points to the first item, and
            // in the loops after, the next element.
            if (item.getItemStock() == 0) {
                // can't do 'iterator.methods from dao class' as it is just a pointer, it has to be assigned the value before methods can be used on it.
                iterator.remove();
                // using the iterators method remove to safely remove the last element returned by the iterator.
                // It is best to use remove() when removing elements during iteration.
                // An ConcurrentModificationException occurs if in a for loop an object is removed (the collection is modified)
                // of the collection that is being iterated through
            }
        }
        return itemsCollection;
    }

    @Override
    public ItemDto fetchItem(String ItemName) throws NoItemInventoryException{    // As itemNames are unique (otherwise their stock would be combined).
        ItemDto itemFetched = itemDao.fetchItem(ItemName);
        if (itemFetched.getItemStock() == 0) {
            NoItemInventoryException e1 = new NoItemInventoryException();
            throw e1;
        }
        return itemFetched;
    }

    public String checkAndVendIfPossible(double userMoney, ItemDto item) {
        if (userMoney >= item.getItemCost()) {
            decreaseItemStock(item);    // Decrease the stock by 1 as the item has been vended.
            double changeDuePennies = (userMoney - item.getItemCost()) * 100;     // QUESTION: I could do and call these two lines in service (within the checkVendPossible method)
            return Change.change(changeDuePennies);
        }
        return null;
    }

    public void decreaseItemStock(ItemDto itemDto) {
        itemDto.setItemStock(itemDto.getItemStock() - 1);
    }

    @Override
    public boolean writeItemsToFile() throws FileNotFoundException {
        return itemDao.writeItemsToFile();      // as itemDao.writeItemsToFile return a boolean value.
    }
}
