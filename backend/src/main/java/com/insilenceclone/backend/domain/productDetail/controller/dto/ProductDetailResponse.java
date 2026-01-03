package com.insilenceclone.backend.domain.productDetail.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insilenceclone.backend.domain.product.entity.Product;

import java.util.List;

public record ProductDetailResponse(
        @JsonProperty("productId") Long productId,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("price") Long price,
        @JsonProperty("imageUrl") String imageUrl,              // 썸네일
        @JsonProperty("detailImages") List<String> detailImages // 상세 이미지들
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
