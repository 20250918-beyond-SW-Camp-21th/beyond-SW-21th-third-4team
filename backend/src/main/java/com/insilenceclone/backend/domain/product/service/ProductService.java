package com.insilenceclone.backend.domain.product.service;

import com.insilenceclone.backend.domain.product.dto.ProductResponseDto;
import java.util.List;

public interface ProductService {
    // 통합 조회 메서드 하나만 정의
    List<ProductResponseDto> getProducts(String category);
}
