package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.Cart;
import com.viendong.BUOI8.model.Product;
import com.viendong.BUOI8.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/product/cart")
@SessionAttributes("cart")
public class CartController {

    @Autowired
    private ProductService productService;

    // Khởi tạo giỏ hàng khi người dùng bắt đầu session
    @ModelAttribute("cart")
    public Cart createCart() {
        return new Cart();
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Integer> addToCart(@RequestParam("productId") Long productId, @ModelAttribute("cart") Cart cart) {
        Product product = productService.getProductById(productId).orElse(null);
        if (product != null) {
            cart.addItem(product, 1); // Thêm sản phẩm với số lượng 1
        }
        return Map.of("quantity", cart.getTotalQuantity()); // Trả về số lượng trong giỏ hàng
    }


    // Lấy thông tin giỏ hàng
    @GetMapping
    public Cart viewCart(@ModelAttribute("cart") Cart cart) {
        return cart; // Trả về giỏ hàng hiện tại dưới dạng JSON
    }
}
