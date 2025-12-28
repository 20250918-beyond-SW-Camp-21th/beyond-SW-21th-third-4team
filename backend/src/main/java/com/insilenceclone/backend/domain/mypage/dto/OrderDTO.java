package com.insilenceclone.backend.domain.mypage.dto;

import com.insilenceclone.backend.domain.order.entity.Order;
import com.insilenceclone.backend.domain.order.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDTO {

    private Long orderId;
    private Long totalPrice;
    private String address;
    private String receiverName;
    private String receiverPhone;
    private String email;
    private String deliveryMessage;
    private OrderStatus status;
    private LocalDateTime orderedAt;
    private List<OrderItemDTO> items;

    public static OrderDTO of(Order order, List<OrderItemDTO> items) {
        return OrderDTO.builder()
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .address(order.getAddress())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .email(order.getEmail())
                .deliveryMessage(order.getDeliveryMessage())
                .status(order.getStatus())
                .orderedAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}