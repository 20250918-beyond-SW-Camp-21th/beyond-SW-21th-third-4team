package com.insilenceclone.backend.domain.productDetail.controller.dto.response;

import java.util.List;

public record ProductDetailResponse(
        Long productId,
        String name,
        String description,
        int price,
        String imageUrl,
        List<ProductOptionResponse> options
) {
}
