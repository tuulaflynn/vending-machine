package service;

import model.ItemDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ItemService {
    List<ItemDto> readItemsFromFile() throws FileNotFoundException, IOException;
    String displayItemsAndPrices();
    ItemDto fetchItem(String itemName);
    boolean decreaseItemStock(int itemId);
    boolean writeItemsToFile();
}
