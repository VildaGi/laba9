package org.example.laba9.service;

import org.example.laba9.model.ShoppingItem;
import org.example.laba9.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ShoppingService {

    @Autowired
    private ShoppingRepository repository;

    public Collection<ShoppingItem> getAllItems() {
        return repository.findAll();
    }

    public ShoppingItem getItemById(Long id) {
        return repository.findById(id);
    }

    public Collection<ShoppingItem> getActiveItems() {
        return repository.findAll().stream()
                .filter(item -> !item.isPurchased())
                .collect(Collectors.toList());
    }

    public Collection<ShoppingItem> getPurchasedItems() {
        return repository.findAll().stream()
                .filter(ShoppingItem::isPurchased)
                .collect(Collectors.toList());
    }

    @Transactional
    public ShoppingItem addItem(String name) {
        ShoppingItem item = new ShoppingItem(null, name, false);
        return repository.save(item);
    }

    @Transactional
    public ShoppingItem updateItem(ShoppingItem item) {
        return repository.save(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deleteAllItems() {
        repository.deleteAll();
    }

    @Transactional
    public ShoppingItem markAsPurchased(Long id) {
        ShoppingItem item = repository.findById(id);
        if (item != null) {
            item.setPurchased(true);
            repository.save(item);
        }
        return item;
    }
}