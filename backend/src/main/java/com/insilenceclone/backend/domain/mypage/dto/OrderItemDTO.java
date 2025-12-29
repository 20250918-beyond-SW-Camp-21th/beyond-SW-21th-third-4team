package com.insilenceclone.backend.domain.mypage.dto;

import com.insilenceclone.backend.domain.order.entity.OrderItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemDTO {

    private Long id;
    private Long productId;
    private int quantity;
    private Long unitPrice;
    private Long totalPrice;

    public static OrderItemDTO from(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.calculateTotalPrice())
                .build();
    }
}