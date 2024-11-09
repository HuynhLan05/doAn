package com.viendong.BUOI8.service;

import com.viendong.BUOI8.model.Cart;
import com.viendong.BUOI8.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    // Các phương thức khác

    // Phương thức đặt hàng
    public Order placeOrder(Cart cart, String customerName, String customerEmail,
                            String customerPhone, String shippingAddress, String paymentMethod) {
        // Lấy tổng giá trị từ giỏ hàng
        double totalAmount = cart.getTotalPrice();  // Gọi getTotalPrice() thay vì getTotalAmount()

        // Tạo đơn hàng mới
        Order newOrder = new Order(customerName, customerEmail, customerPhone, shippingAddress,
                paymentMethod, totalAmount, Order.OrderStatus.PENDING);

        // Lưu đơn hàng vào cơ sở dữ liệu (ví dụ, save vào DB nếu có repository)
        // orderRepository.save(newOrder);

        return newOrder;
    }

    public void saveOrder(Order newOrder) {
    }
}
