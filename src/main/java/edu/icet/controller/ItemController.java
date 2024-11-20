package edu.icet.controller;

import edu.icet.dto.Item;
import edu.icet.dto.StockUpdate;
import edu.icet.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
@CrossOrigin
public class ItemController {

    final ItemService service;

    @PostMapping("/add-item")
    public void addItem(@RequestBody Item item){
        service.addItem(item);
    }

    @PostMapping("/update-stock")
    public ResponseEntity<Void> updateStock(@RequestBody List<StockUpdate> updates) {
        service.updateStock(updates);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all-active-items")
    public List<Item> getAllActiveItems(){
        return service.getAllActiveItems();
    }

    @DeleteMapping("/delete-item/{id}")
    public ResponseEntity<Boolean> deleteItemById(@PathVariable String id){
        boolean deactivated = service.deactivateItem(id);
        return ResponseEntity.ok(deactivated);
    }

    @PutMapping("/update-item")
    public void updateItem(@RequestBody Item item){
        service.updateItemById(item);
    }

    @GetMapping("/items-by-category/{category}")
    public List<Item> getItemsByCategory(@PathVariable String category) {
        return service.getItemsByCategory(category);
    }
}
