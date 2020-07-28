package com.imperva.vending;

import com.imperva.vending.db.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class Endpoints {

    @Autowired
    private MachineService machineService;

    @PostMapping("/dispense")
    public ResponseEntity<Void> dispenseItem(String cellCode) {
        boolean available = machineService.buyItem(cellCode);
        if (available) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/restock")
    public ResponseEntity<Void> restockCell(String cellCode, int quantity, int itemId) {
        boolean result = machineService.restock(cellCode, quantity, itemId);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/addItem", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> addNewItem(String name, double price) {
        Item item = machineService.addNewItem(name, price);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/getQuantityOfItem")
    public ResponseEntity<Integer> getItemQuantity(String name) {
        return ResponseEntity.ok(machineService.getItemQuantity(name));
    }

    @PostMapping(value = "/getQuantity", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String , Integer>> getPresentItems() {
        return ResponseEntity.ok(machineService.getPresentItems());
    }

    @PostMapping(value = "/removeItem")
    public ResponseEntity<Boolean> removeItem(String name) {
        boolean result = machineService.removeItem(name);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
