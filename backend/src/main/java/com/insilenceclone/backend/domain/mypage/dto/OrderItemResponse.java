package com.insilenceclone.backend.domain.mypage.dto;

import com.insilenceclone.backend.domain.order.entity.OrderItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemResponse {

    private Long id;
    private Long productId;
    private int quantity;
    private Long unitPrice;
    private Long totalPrice;

    public static OrderItemResponse from(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.calculateTotalPrice())
                .build();
    }
}