package com.viendong.BUOI8.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
