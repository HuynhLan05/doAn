package com.viendong.BUOI8.dto;

import com.viendong.BUOI8.model.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private String paymentMethod;
    private double totalAmount;
    private List<OrderDetail> orderDetails;  // Danh sách chi tiết đơn hàng
}
