package com.viendong.BUOI8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private String paymentMethod;
    private double totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(String customerName, String customerEmail, String customerPhone, String shippingAddress, String paymentMethod, double totalAmount, OrderStatus orderStatus) {
    }

    public enum OrderStatus {
        PENDING, SHIPPED, DELIVERED, CANCELED
    }
}
