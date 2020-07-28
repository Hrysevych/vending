package com.imperva.vending;

import com.imperva.vending.db.Cell;
import com.imperva.vending.db.CellRepository;
import com.imperva.vending.db.Item;
import com.imperva.vending.db.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MachineService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CellRepository cellRepository;

    public boolean buyItem(String cellCode) {
        Cell cell = cellRepository.findByCellCode(cellCode);
        if (cell.getQuantity() > 0) {
            cell.setQuantity(cell.getQuantity() - 1);
            cellRepository.save(cell);
            return true;
        } else {
            return false;
        }
    }

    public boolean restock(String cellCode, int quantity, int itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            return false;
        }
        Cell cell = cellRepository.findByCellCode(cellCode);
        if (cell == null) {
            cell = new Cell();
            cell.setCellCode(cellCode);
            cell.setQuantity(quantity);
        }
        cell.setQuantity(cell.getQuantity() + quantity);
        cell.setItem(item);
        cellRepository.save(cell);
        return true;
    }

    public Item addNewItem(String name, double price) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        return itemRepository.save(item);
    }

    public Integer getItemQuantity(String name) {
        return itemRepository.findByName(name).getCells().stream().mapToInt(Cell::getQuantity).sum();
    }

    public Map<String, Integer> getPresentItems() {
        Iterable<Item> rawItems = itemRepository.findAll();
        Map<String, Integer> output = new HashMap<>();
        for (Item item : rawItems) {
            int q = item.getCells().stream().mapToInt(Cell::getQuantity).sum();
            if (q > 0) {
                output.put(item.getName(), q);
            }
        }
        return output;
    }

    public boolean removeItem(String name) {
        Item item = itemRepository.findByName(name);
        if (item == null) {
            return false;
        }
        itemRepository.delete(item);
        return true;
    }
}
