package com.insilenceclone.backend.domain.productDetail.controller.dto;

import com.insilenceclone.backend.domain.product.entity.Product;

import java.util.List;

public record ProductDetailResponse(
        Long productId,
        String name,
        String description,
        Long price,
        String imageUrl,           // 썸네일
        List<String> detailImages // 상세 이미지들
) {
    public static ProductDetailResponse of(Product info, List<String> detailImages) {
        return new ProductDetailResponse(
                info.getId(),
                info.getName(),
                info.getDescription(),
                info.getPrice(),
                info.getImageUrl(),   // 썸네일 매핑
                detailImages      // 리스트 매핑
        );
    }
}
