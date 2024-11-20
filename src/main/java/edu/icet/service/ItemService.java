package edu.icet.service;

import edu.icet.dto.Item;
import edu.icet.dto.StockUpdate;

import java.util.List;

public interface ItemService {
    void addItem(Item item);
    List<Item> getAllActiveItems();
    boolean deactivateItem(String id);
    void updateItemById(Item item);
    List<Item> getItemsByCategory(String category);
    void updateStock(List<StockUpdate> updates);
}
