package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.*;
import com.viendong.BUOI8.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private AdminOrderService adminOrderService;
    private final OrderService orderService;

    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "users/index";
    }

    @GetMapping("/users/about")
    public String about() {
        return "users/about";
    }

    @GetMapping("/users/product")
    public String product() {
        return "users/product";
    }

    @GetMapping("/users/product/view_detail")
    public String viewDetail(@RequestParam("id") Long productId, Model model) {
        Product product = productService.findById(productId);
        if (product != null) {
            model.addAttribute("product", product);
        } else {
            // Xử lý nếu không tìm thấy sản phẩm
            model.addAttribute("error", "Product not found");
        }
        return "users/product_view_detail"; // Tên trang chi tiết
    }
    @GetMapping("/users/product/orders")
    public String orders(@RequestParam("id") Long productId, Model model) {
        Product product = productService.findById(productId);
        if (product != null) {
            model.addAttribute("product", product);  // Gửi sản phẩm vào model
        } else {
            model.addAttribute("error", "Sản phẩm không tồn tại");
        }
        return "users/order_form";
    }

    @GetMapping("/users/product/order_detail")
    public String detail() {
        return "users/order_success";
    }

    @GetMapping("/users/product/cart")
    public String cart() {
        return "users/cart";
    }

    @GetMapping("/users/blog")
    public String blog() {
        return "users/blog";
    }

    @GetMapping("/users/feature")
    public String feature() {
        return "users/feature";
    }

    @GetMapping("/users/testimonial")
    public String testimonial() {
        return "users/testimonial";
    }

    @GetMapping("/users/404")
    public String pageError() {
        return "users/404";
    }

    @GetMapping("/users/contact")
    public String contact() {
        return "users/contact";
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/users/product/orders/{id}")
    public String placeOrder(@PathVariable("id") Long productId,
                             @RequestParam String customerName,
                             @RequestParam String customerPhone,
                             @RequestParam String shippingAddress,
                             @RequestParam String customerEmail,
                             @RequestParam String paymentMethod,
                             Model model) {
        // Kiểm tra sản phẩm và tạo đơn hàng
        Optional<Product> productOpt = productService.getProductById(productId);
        if (productOpt.isEmpty()) {
            model.addAttribute("error", "Sản phẩm không tồn tại.");
            return "users/order_form";
        }

        Product product = productOpt.get();
        double totalAmount = product.getPrice();  // Giả sử chỉ có một sản phẩm

        // Tạo và lưu đơn hàng
        Order newOrder = new Order(customerName, customerEmail, customerPhone, shippingAddress,
                paymentMethod, totalAmount, Order.OrderStatus.PENDING);
        orderService.saveOrder(newOrder);  // Gọi orderService để lưu đơn hàng

        // Thêm thông tin đơn hàng vào model để hiển thị thông báo
        model.addAttribute("message", "Đặt hàng thành công!");

        // Quay lại trang thông báo thành công
        return "users/order_form";  // Hoặc chuyển hướng đến trang thông báo thành công khác
    }



    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "users/register";
        }
        userService.save(user);
        userService.setDefaultRole(user.getUsername());
        return "redirect:/login";
    }
}
