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

    // Create an object 'itemDao' for which we can call methods of ItemDao on. This means the service layer can call the dao layer.
    // Only need to create one object of the dao class for this reason.
    ItemDao itemDao;

    public ItemServiceImpl() {
        itemDao = new ItemDaoImpl();
    }

    @Override
    public List<ItemDto> readItemsFromFile() throws FileNotFoundException, IOException {
        return itemDao.readItemsFromFile();
    }

    @Override
    public List<ItemDto> fetchItemsNonZeroStock() {
        List<ItemDto> itemsCollection = itemDao.fetchItems();

        // Creating an iterator for the collection itemsCollection, it initially points to before the first element.
        Iterator<ItemDto> iterator = itemsCollection.iterator();

        while (iterator.hasNext()) {
            ItemDto item = iterator.next();
            // iterator.next() returns the next element in the collection and moves the iterator to the next position.
            // As the iterator initially points to before the first item, it points to the first item, and in the loops after, the next element.
            if (item.getItemStock() == 0) {
                // Can't do 'iterator.methods from dao class' as it is just a pointer, it has to be assigned the value before methods can be used on it.
                iterator.remove();
                // Iterators method remove safely remove the last element returned by the iterator.
                // It is best to use remove() when removing elements from the collection you are iterating through.
                // ConcurrentModificationException occurs if an object is removed from the collection being iterated through in a for loop.
            }
        }

        /* EASIER WAY TO DO IT WITHOUT ITERATOR:
        itemsCollection.removeIf(item -> item.getItemStock() == 0);
         */
        return itemsCollection;
    }

    @Override
    public ItemDto fetchItem(String itemName) throws NoItemInventoryException {
        // Search by itemName as item name is unique (otherwise their stock would be combined).
        ItemDto itemFetched = itemDao.fetchItem(itemName);

        if (itemFetched.getItemStock() == 0) {
            NoItemInventoryException e1 = new NoItemInventoryException();
            throw e1;
        }

        return itemFetched;
    }

    public String checkAndVendIfPossible(double userMoney, ItemDto item) {
        if (userMoney >= item.getItemCost()) {
            decreaseItemStock(item);    // Decrease the stock by 1 as the item has been vended.
            // QUESTION: I could do and call the following 2 lines in view layer. Which is better?
            double changeDuePennies = (userMoney - item.getItemCost()) * 100;
            return Change.change(changeDuePennies);
        }
        return null;
    }

    public void decreaseItemStock(ItemDto itemDto) {
        itemDto.setItemStock(itemDto.getItemStock() - 1);
    }

    @Override
    public boolean writeItemsToFile() throws FileNotFoundException {
        return itemDao.writeItemsToFile();      // As itemDao.writeItemsToFile return a boolean value.
    }
}
