package com.insilenceclone.backend.domain.product.service;

import com.insilenceclone.backend.domain.product.dto.ProductResponseDto;
import java.util.List;

public interface ProductService {
    // 전체 조회 및 카테고리별 메서드
    List<ProductResponseDto> getProducts(String category);

}
