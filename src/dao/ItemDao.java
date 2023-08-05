package dao;

import model.ItemDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ItemDao {
    List<ItemDto> readItemsFromFile() throws FileNotFoundException, IOException;
    ArrayList<Object> displayItemsAndPrices();
    ItemDto fetchItem(int itemId);
    boolean decreaseItemStock(int itemId);
    boolean writeItemsToFile();

}
