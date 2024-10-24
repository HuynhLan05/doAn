package com.viendong.BUOI8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class AdminOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private String paymentMethod; // Phương thức thanh toán
    private double totalAmount; // Tổng tiền

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    // Thêm trạng thái
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Constructor không có OrderDetail
    public AdminOrder(String customerName, String customerEmail, String customerPhone,
                      String shippingAddress, String paymentMethod, double totalAmount, OrderStatus status) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Enum cho trạng thái đơn hàng
    public enum OrderStatus {
        PENDING, SHIPPED, DELIVERED, CANCELED
    }
}
