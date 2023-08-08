package service;

import exceptions.NoItemInventoryException;
import model.ItemDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ItemService {
    List<ItemDto> readItemsFromFile() throws FileNotFoundException, IOException;
    List<ItemDto> fetchItemsNonZeroStock ();
    ItemDto fetchItem(String itemName) throws NoItemInventoryException;
    String checkAndVendIfPossible(double userMoney, ItemDto item);
    void decreaseItemStock(ItemDto itemDto);
    boolean writeItemsToFile() throws FileNotFoundException;
}
