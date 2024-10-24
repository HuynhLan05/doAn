package com.viendong.BUOI8.repository;

import com.viendong.BUOI8.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>   {
}
