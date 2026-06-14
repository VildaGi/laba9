package org.example.laba9.service;

import org.example.laba9.model.ShoppingItem;
import org.example.laba9.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingService {
    @Autowired
    private ShoppingRepository repository;

    public Collection<ShoppingItem> getAllItems() {
        return repository.findAll();
    }

    public ShoppingItem addItem(String name) {
        ShoppingItem item = new ShoppingItem(null, name, false);
        return repository.save(item);
    }

    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    public ShoppingItem markAsPurchased(Long id) {
        ShoppingItem item = repository.findById(id);
        if (item != null) {
            item.setPurchased(true);
            repository.save(item);
        }
        return item;
    }
}