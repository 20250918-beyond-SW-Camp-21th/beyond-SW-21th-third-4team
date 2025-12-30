package com.insilenceclone.backend.domain.productDetail.repository;

import com.insilenceclone.backend.domain.productDetail.entity.ProductImage;
import com.insilenceclone.backend.domain.productDetail.enums.ProductImageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    // 상품 이미지 조회
    List<ProductImage> findByProductIdAndStatusOrderBySortOrderAsc(Long productId, ProductImageStatus status);

}
