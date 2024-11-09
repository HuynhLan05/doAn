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

    // Thêm sản phẩm vào giỏ hàng, nếu sản phẩm đã tồn tại, tăng số lượng
    public void addItem(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            return; // Không thêm sản phẩm nếu không hợp lệ
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ chưa
        for (CartItem item : items) {
            if (item.getProduct().getId() != null && item.getProduct().getId().equals(product.getId())) {
                // Nếu sản phẩm đã tồn tại, tăng số lượng
                item.setQuantity(item.getQuantity() + quantity);
                updateTotalPrice();
                return;
            }
        }

        // Nếu sản phẩm chưa tồn tại, thêm mới vào giỏ
        items.add(new CartItem(product, quantity));
        updateTotalPrice();
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeItem(Long productId) {
        if (productId == null) {
            return; // Tránh xóa sản phẩm nếu id không hợp lệ
        }

        items.removeIf(item -> item.getProduct().getId() != null && item.getProduct().getId().equals(productId));
        updateTotalPrice();
    }

    // Giảm số lượng sản phẩm trong giỏ
    public void reduceQuantity(Long productId, int quantity) {
        if (quantity <= 0) {
            return; // Không giảm nếu số lượng không hợp lệ
        }

        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                int newQuantity = item.getQuantity() - quantity;
                if (newQuantity <= 0) {
                    removeItem(productId); // Nếu số lượng giảm xuống <= 0, xóa sản phẩm khỏi giỏ
                } else {
                    item.setQuantity(newQuantity);
                    updateTotalPrice();
                }
                return;
            }
        }
    }

    // Cập nhật tổng giá trị của giỏ hàng
    private void updateTotalPrice() {
        totalPrice = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // Trả về tổng số lượng sản phẩm trong giỏ hàng
    public Integer getTotalQuantity() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    // Xóa tất cả sản phẩm trong giỏ hàng
    public void clearCart() {
        items.clear();
        updateTotalPrice();
    }
}
