package com.viendong.BUOI8.service;

import com.viendong.BUOI8.model.OrderDetail;
import com.viendong.BUOI8.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
