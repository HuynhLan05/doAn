package com.viendong.BUOI8.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private AdminOrder order;

    private int quantity;
    private double totalPrice;

    public OrderDetail(Product product, AdminOrder order, int quantity, double totalPrice) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
