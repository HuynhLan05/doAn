package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.model.AdminOrder;
import com.viendong.BUOI8.model.Order;
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
        return "admin/orders";  // Trang hiển thị danh sách đơn hàng của admin
    }




    @GetMapping("/{id}")
    public String showOrder(@PathVariable Long id, Model model) {
        AdminOrder order = orderService.getOrderById(id);
        if (order == null) {
            model.addAttribute("error", "Đơn hàng không tồn tại!");
            return "error";  // Nếu không tìm thấy đơn hàng, chuyển đến trang lỗi
        }
        model.addAttribute("order", order);  // Truyền đơn hàng vào view
        return "orders/show";  // Trang hiển thị chi tiết đơn hàng
    }


    @GetMapping("/{id}/edit")
    public String editOrder(@PathVariable Long id, Model model) {
        AdminOrder order = orderService.getOrderById(id);
        if (order == null) {
            // Thêm thông báo không tìm thấy
            model.addAttribute("error", "Đơn hàng không tồn tại!");
            return "error"; // Chuyển đến view thông báo lỗi
        }
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
                              @RequestParam AdminOrder.OrderStatus status) {
        AdminOrder order = new AdminOrder(customerName, customerEmail, customerPhone, shippingAddress, paymentMethod, totalAmount, status);
        orderService.saveOrder(order);
        // Thêm thông báo thành công nếu cần
        return "redirect:/admin/orders"; // Chuyển hướng về danh sách đơn hàng
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
        if (order == null) {
            // Thêm thông báo không tìm thấy
            return "redirect:/admin/orders?error=Đơn hàng không tồn tại!"; // Chuyển hướng với thông báo lỗi
        }
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(paymentMethod);
        order.setTotalAmount(totalAmount);
        order.setStatus(status);
        orderService.saveOrder(order);
        // Thêm thông báo thành công nếu cần
        return "redirect:/admin/orders"; // Chuyển hướng về danh sách đơn hàng
    }

    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        if (orderService.getOrderById(id) == null) {
            return "redirect:/admin/orders?error=Đơn hàng không tồn tại!"; // Chuyển hướng với thông báo lỗi
        }
        orderService.deleteOrder(id);
        // Thêm thông báo thành công nếu cần
        return "redirect:/admin/orders"; // Chuyển hướng về danh sách đơn hàng
    }
}
