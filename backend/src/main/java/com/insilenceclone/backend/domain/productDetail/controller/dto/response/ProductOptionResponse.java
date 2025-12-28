package com.insilenceclone.backend.domain.productDetail.controller.dto.response;

public record ProductOptionResponse(
        Long optionId,
        String color,
        String size,
        int additionalPrice,
        boolean available
) {
}
