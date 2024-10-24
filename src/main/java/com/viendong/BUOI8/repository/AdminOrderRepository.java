package com.viendong.BUOI8.repository;

import com.viendong.BUOI8.model.AdminOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminOrderRepository extends JpaRepository<AdminOrder, Long> {
    List<AdminOrder> findByStatus(AdminOrder.OrderStatus status);
}
