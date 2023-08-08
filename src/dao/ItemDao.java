package dao;

import model.ItemDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ItemDao {
    List<ItemDto> readItemsFromFile() throws FileNotFoundException, IOException;
    List<ItemDto> fetchItems();
    ItemDto fetchItem(String itemName);
    boolean writeItemsToFile() throws FileNotFoundException;

}
