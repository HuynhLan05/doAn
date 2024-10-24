package com.viendong.BUOI8.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {
    private List<CartItem> items = new ArrayList<>();
    private double totalPrice;

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                updateTotalPrice();
                return;
            }
        }
        items.add(new CartItem(product, quantity));
        updateTotalPrice();
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        totalPrice = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}

