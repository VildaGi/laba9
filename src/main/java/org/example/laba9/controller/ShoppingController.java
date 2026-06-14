package org.example.laba9.controller;

import org.example.laba9.model.ShoppingItem;
import org.example.laba9.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/shopping")
@CrossOrigin(origins = "*")
public class ShoppingController {
    @Autowired
    private ShoppingService service;

    @GetMapping
    public ResponseEntity<Collection<ShoppingItem>> getAllItems() {
        return ResponseEntity.ok(service.getAllItems());
    }

    @PostMapping
    public ResponseEntity<ShoppingItem> addItem(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ShoppingItem item = service.addItem(name.trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/purchase")
    public ResponseEntity<ShoppingItem> markAsPurchased(@PathVariable Long id) {
        ShoppingItem item = service.markAsPurchased(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }
}