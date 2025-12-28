package com.insilenceclone.backend.domain.product.repository;

import com.insilenceclone.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 카테고리별 조회 메서드
    List<Product> findByCategory(String category);
}
