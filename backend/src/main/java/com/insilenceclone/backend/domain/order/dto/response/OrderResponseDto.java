package com.insilenceclone.backend.domain.order.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
// 주문시 프론트에 전달할 정보
public class OrderResponseDto {

    private String orderId;
    private String orderStatus;
    private Long totalPrice;
    private LocalDateTime orderedAt;
}
