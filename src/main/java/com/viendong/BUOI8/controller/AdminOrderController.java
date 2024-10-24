package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.AdminOrder;
import com.viendong.BUOI8.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {
    @Autowired
    private AdminOrderService orderService;

    @GetMapping
    public String listOrders(Model model) {
        List<AdminOrder> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/index"; // Giữ nguyên view
    }

    @GetMapping("/{id}")
    public String showOrder(@PathVariable Long id, Model model) {
        AdminOrder order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "orders/show"; // View chi tiết đơn hàng
    }
    @GetMapping("/{id}/edit")
    public String editOrder(@PathVariable Long id, Model model) {
        AdminOrder order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "orders/edit"; // Đường dẫn tới view chỉnh sửa đơn hàng
    }

    @PostMapping
    public String createOrder(@RequestParam String customerName,
                              @RequestParam String customerEmail,
                              @RequestParam String customerPhone,
                              @RequestParam String shippingAddress,
                              @RequestParam String paymentMethod,
                              @RequestParam double totalAmount,
                              @RequestParam AdminOrder.OrderStatus status) { // Thêm trạng thái
        AdminOrder order = new AdminOrder(customerName, customerEmail, customerPhone, shippingAddress, paymentMethod, totalAmount, status);
        orderService.saveOrder(order);
        return "redirect:/admin/orders";
    }

    @PostMapping("/{id}/update")
    public String updateOrder(@PathVariable Long id,
                              @RequestParam String customerName,
                              @RequestParam String customerEmail,
                              @RequestParam String customerPhone,
                              @RequestParam String shippingAddress,
                              @RequestParam String paymentMethod,
                              @RequestParam double totalAmount,
                              @RequestParam AdminOrder.OrderStatus status) {
        AdminOrder order = orderService.getOrderById(id);
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setTotalAmount(totalAmount);
        order.setStatus(status);
        orderService.saveOrder(order);
        return "redirect:/admin/orders";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/admin/orders";
    }
}
