package com.viendong.BUOI8.controller;

import com.viendong.BUOI8.dto.OrderRequest;
import com.viendong.BUOI8.model.AdminOrder;
import com.viendong.BUOI8.model.Order;
import com.viendong.BUOI8.model.OrderDetail;
import com.viendong.BUOI8.repository.AdminOrderRepository;
import com.viendong.BUOI8.repository.OrderRepository;
import com.viendong.BUOI8.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private AdminOrderRepository adminOrderRepository;

    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        // Tạo đơn hàng
        Order order = new Order(
                orderRequest.getCustomerName(),
                orderRequest.getCustomerEmail(),
                orderRequest.getCustomerPhone(),
                orderRequest.getShippingAddress(),
                orderRequest.getPaymentMethod(),
                orderRequest.getTotalAmount(),
                Order.OrderStatus.PENDING
        );

        // Lưu đơn hàng vào cơ sở dữ liệu
        Order savedOrder = orderRepository.save(order);

        // Lưu chi tiết đơn hàng
        List<OrderDetail> orderDetails = orderRequest.getOrderDetails();
        for (OrderDetail detail : orderDetails) {
            detail.setOrder(savedOrder); // Liên kết chi tiết đơn hàng với đơn hàng
            orderDetailRepository.save(detail);
        }

        // Cập nhật thông tin quản trị
        AdminOrder adminOrder = new AdminOrder(
                orderRequest.getCustomerName(),
                orderRequest.getCustomerEmail(),
                orderRequest.getCustomerPhone(),
                orderRequest.getShippingAddress(),
                orderRequest.getPaymentMethod(),
                orderRequest.getTotalAmount(),
                AdminOrder.OrderStatus.PENDING
        );
        adminOrderRepository.save(adminOrder);

        return savedOrder;
    }
}
