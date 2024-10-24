package com.viendong.BUOI8.service;

import com.viendong.BUOI8.model.AdminOrder;
import com.viendong.BUOI8.repository.AdminOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrderService {
    @Autowired
    private AdminOrderRepository orderRepository;

    public List<AdminOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public AdminOrder getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void saveOrder(AdminOrder order) {
        orderRepository.save(order);
    }
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
