package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.CartItem;
import com.viendong.BUOI8.service.CartService;
import com.viendong.BUOI8.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        // Bạn có thể thêm thông tin cần thiết vào model trước khi hiển thị trang checkout nếu cần
        return "cart/checkout"; // Đường dẫn tới trang kiểm tra hàng
    }

    @PostMapping("/submit")
    public String submitOrder(String customerName, String customerEmail, String customerPhone,
                              String shippingAddress, String paymentMethod) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect nếu giỏ hàng trống
        }

        orderService.createOrder(customerName, customerEmail, customerPhone,
                shippingAddress, paymentMethod, cartItems);
        return "redirect:/order/confirmation"; // Chuyển hướng đến trang xác nhận đơn hàng
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "cart/order-confirmation"; // Đường dẫn đến trang xác nhận đơn hàng
    }
}