package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.Cart;
import com.viendong.BUOI8.model.AdminOrder;
import com.viendong.BUOI8.model.Order;
import com.viendong.BUOI8.model.Product;
import com.viendong.BUOI8.service.OrderService;
import com.viendong.BUOI8.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderUserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cart", cart);
        return "cart/view";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Optional<Product> product = productService.getProductById(productId);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        cart.addItem(product.orElse(null), quantity);
        return "redirect:/order"; // Redirect tới trang giỏ hàng
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String customerName,
                           @RequestParam String customerEmail,
                           @RequestParam String customerPhone,
                           @RequestParam String shippingAddress,
                           @RequestParam String paymentMethod,
                           HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            return "redirect:/order"; // Redirect về trang giỏ hàng nếu không có sản phẩm
        }

        // Sử dụng OrderService để đặt hàng
        Order order = orderService.placeOrder(cart, customerName, customerEmail, customerPhone, shippingAddress, paymentMethod);

        // Xóa giỏ hàng sau khi đặt hàng
        session.removeAttribute("cart");

        model.addAttribute("order", order);
        model.addAttribute("message", "Đặt hàng thành công!");
        return "cart/confirmation"; // Trang xác nhận đơn hàng
    }
}
