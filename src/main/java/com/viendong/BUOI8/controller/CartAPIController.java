package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartAPIController {

    // API để thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItem cartItem, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        boolean productExists = false;

        for (CartItem item : cart) {
            // Sử dụng item.getProduct().getName() thay vì item.getName()
            if (item.getProduct().getName().equals(cartItem.getProduct().getName())) {
                item.setQuantity(item.getQuantity() + 1);
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            cart.add(cartItem);
        }

        session.setAttribute("cart", cart);
        return ResponseEntity.ok(cart);
    }


    // API để lấy giỏ hàng
    @GetMapping
    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        return cart != null ? cart : new ArrayList<>();
    }

    // API để xóa sản phẩm khỏi giỏ hàng (nếu cần)
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestParam String productName, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart is empty");
        }

        // Sử dụng item.getProduct().getName() thay vì item.getName()
        cart.removeIf(item -> item.getProduct().getName().equals(productName));
        session.setAttribute("cart", cart);
        return ResponseEntity.ok(cart);
    }
}
