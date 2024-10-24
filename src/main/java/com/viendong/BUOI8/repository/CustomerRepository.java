package com.viendong.BUOI8.repository;

import com.viendong.BUOI8.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
