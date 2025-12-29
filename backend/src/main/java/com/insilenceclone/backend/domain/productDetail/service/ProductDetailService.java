package com.insilenceclone.backend.domain.productDetail.service;

import com.insilenceclone.backend.domain.productDetail.controller.dto.ProductDetailResponse;
import com.insilenceclone.backend.domain.productDetail.usecase.reader.ProductDetailReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDetailService {

    private final ProductDetailReader reader;

    // 상품 상세 정보 조회
    public ProductDetailResponse getDetail(Long productId) {
        return reader.read(productId);
    }
}
