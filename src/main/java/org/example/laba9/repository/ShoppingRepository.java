package org.example.laba9.repository;

import org.example.laba9.model.ShoppingItem;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Collection;

@Repository
public class ShoppingRepository {
    private final ConcurrentHashMap<Long, ShoppingItem> items = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Collection<ShoppingItem> findAll() {
        return items.values();
    }

    public ShoppingItem save(ShoppingItem item) {
        if (item.getId() == null) {
            item.setId(idGenerator.getAndIncrement());
        }
        items.put(item.getId(), item);
        return item;
    }

    public ShoppingItem findById(Long id) {
        return items.get(id);
    }

    public void deleteById(Long id) {
        items.remove(id);
    }
}