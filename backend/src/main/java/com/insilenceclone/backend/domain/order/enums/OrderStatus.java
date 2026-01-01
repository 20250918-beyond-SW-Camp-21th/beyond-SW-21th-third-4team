package com.insilenceclone.backend.domain.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    ORDERED("주문완료"),
    PAYMENT_COMLETED("결제완료"),
    PREPARING("배송 준비중"),
    SHIPPING("배송 중"),
    DELIVERED("배송 완료"),
    CANCELLED("주문 취소");

    private final String description;
}
