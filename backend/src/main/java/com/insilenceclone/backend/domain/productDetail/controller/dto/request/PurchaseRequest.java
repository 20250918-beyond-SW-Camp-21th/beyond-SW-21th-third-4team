package com.insilenceclone.backend.domain.productDetail.controller.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PurchaseRequest(
        @NotNull Long productId,
        @NotNull Long optionId,
        @Min(1) int quantity
) {
}
