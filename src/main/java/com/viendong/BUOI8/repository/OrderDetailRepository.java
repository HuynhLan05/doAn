package com.viendong.BUOI8.repository;

import com.viendong.BUOI8.model.AdminOrder;
import com.viendong.BUOI8.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(AdminOrder order);
}
