package com.insilenceclone.backend.domain.product.repository;

import com.insilenceclone.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReposiory extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
}
