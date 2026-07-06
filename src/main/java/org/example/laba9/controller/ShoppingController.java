package org.example.laba9.controller;

import org.example.laba9.dto.ShoppingItemDTO;
import org.example.laba9.model.ShoppingItem;
import org.example.laba9.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shopping-items")
@CrossOrigin(origins = "*")
public class ShoppingController {

    @Autowired
    private ShoppingService service;

    @GetMapping
    public ResponseEntity<Collection<ShoppingItemDTO>> getAllItems() {
        Collection<ShoppingItem> items = service.getAllItems();
        Collection<ShoppingItemDTO> dtos = items.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingItemDTO> getItemById(@PathVariable Long id) {
        ShoppingItem item = service.getItemById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(item));
    }

    @PostMapping
    public ResponseEntity<ShoppingItemDTO> createItem(@Valid @RequestBody ShoppingItemDTO itemDTO) {
        if (itemDTO.getName() == null || itemDTO.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ShoppingItem created = service.addItem(itemDTO.getName().trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingItemDTO> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody ShoppingItemDTO itemDTO) {
        ShoppingItem item = service.getItemById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        item.setName(itemDTO.getName());
        item.setPurchased(itemDTO.isPurchased());
        ShoppingItem updated = service.updateItem(item);
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @PatchMapping("/{id}/purchase")
    public ResponseEntity<ShoppingItemDTO> markAsPurchased(@PathVariable Long id) {
        ShoppingItem item = service.markAsPurchased(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        ShoppingItem item = service.getItemById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Collection<ShoppingItemDTO>> filterItems(
            @RequestParam(required = false) String status) {
        Collection<ShoppingItem> items;

        if ("active".equalsIgnoreCase(status)) {
            items = service.getActiveItems();
        } else if ("purchased".equalsIgnoreCase(status)) {
            items = service.getPurchasedItems();
        } else {
            items = service.getAllItems();
        }

        Collection<ShoppingItemDTO> dtos = items.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllItems() {
        service.deleteAllItems();
        return ResponseEntity.noContent().build();
    }

    private ShoppingItemDTO convertToDTO(ShoppingItem item) {
        return new ShoppingItemDTO(item.getId(), item.getName(), item.isPurchased());
    }
}