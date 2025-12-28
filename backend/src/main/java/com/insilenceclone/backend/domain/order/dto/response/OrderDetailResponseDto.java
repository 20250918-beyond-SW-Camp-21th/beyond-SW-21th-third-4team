package com.insilenceclone.backend.domain.order.dto.response;

import com.insilenceclone.backend.domain.order.entity.Order;
import com.insilenceclone.backend.domain.order.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
// 상세 주문 조회시 프론트에 전달할 정보
public class OrderDetailResponseDto {

    private Long orderId;
    private OrderStatus status;
    private Long totalPrice;
    private LocalDateTime orderedAt;

    private String address;
    private String receiverName;
    private String receiverPhone;
    private String deliveryMessage;

    private List<OrderItemDetailResponseDto> orderItems;

    public static OrderDetailResponseDto from(Order order, List<OrderItemDetailResponseDto> items) {
        return OrderDetailResponseDto.builder()
                .orderId(order.getId())
                .orderedAt(order.getCreatedAt())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .address(order.getAddress())
                .deliveryMessage(order.getDeliveryMessage())
                .orderItems(items)
                .build();
    }
}
