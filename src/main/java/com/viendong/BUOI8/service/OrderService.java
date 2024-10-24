package com.viendong.BUOI8.service;

import com.viendong.BUOI8.model.AdminOrder;
import com.viendong.BUOI8.model.Cart;
import com.viendong.BUOI8.model.CartItem;
import com.viendong.BUOI8.model.OrderDetail;
import com.viendong.BUOI8.repository.AdminOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private AdminOrderRepository orderRepository;

    public AdminOrder placeOrder(Cart cart, String customerName, String customerEmail, String customerPhone,
                                 String shippingAddress, String paymentMethod) {
        return null;
    }

    public void createOrder(String customerName, String customerEmail, String customerPhone, String shippingAddress, String paymentMethod, List<CartItem> cartItems) {
    }
}
