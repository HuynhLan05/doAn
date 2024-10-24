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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private String paymentMethod; // Phương thức thanh toán

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // Cascade để tự động lưu OrderDetail
    private List<OrderDetail> orderDetails;
}