package com.insilenceclone.backend.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemDetailResponseDto {

    private Long productId;
    private String productName;
    private Integer quantity;
    private Long unitPrice;
    private Long totalAmount;
}
