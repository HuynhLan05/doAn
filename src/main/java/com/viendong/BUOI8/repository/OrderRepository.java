package com.viendong.BUOI8.repository;

import com.viendong.BUOI8.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Các phương thức truy vấn nếu cần
}
