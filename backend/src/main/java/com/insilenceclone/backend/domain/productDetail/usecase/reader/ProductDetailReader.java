package com.insilenceclone.backend.domain.productDetail.usecase.reader;

import com.insilenceclone.backend.domain.productDetail.controller.dto.ProductDetailResponse;

public interface ProductDetailReader {
    ProductDetailResponse read(Long productId);
}
