package org.example.laba9.dto;

import jakarta.validation.constraints.NotBlank;

public class ShoppingItemDTO {
    private Long id;

    @NotBlank(message = "Название товара не может быть пустым")
    private String name;
    private boolean purchased;

    public ShoppingItemDTO() {}

    public ShoppingItemDTO(Long id, String name, boolean purchased) {
        this.id = id;
        this.name = name;
        this.purchased = purchased;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isPurchased() { return purchased; }
    public void setPurchased(boolean purchased) { this.purchased = purchased; }
}