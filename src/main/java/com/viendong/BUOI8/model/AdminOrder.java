package com.viendong.BUOI8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "admin_orders")
public class AdminOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private String paymentMethod;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Constructor với các tham số
    public AdminOrder(String customerName, String customerEmail, String customerPhone,
                      String shippingAddress, String paymentMethod, double totalAmount,
                      OrderStatus orderStatus) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.status = orderStatus;
    }

    // Enum cho trạng thái đơn hàng
    public enum OrderStatus {
        PENDING, SHIPPED, DELIVERED, CANCELED
    }
}
