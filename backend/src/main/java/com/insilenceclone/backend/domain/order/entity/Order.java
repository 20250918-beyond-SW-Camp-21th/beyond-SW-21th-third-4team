package com.insilenceclone.backend.domain.order.entity;

import com.insilenceclone.backend.common.entity.BaseTimeEntity;
import com.insilenceclone.backend.domain.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
// Builder 패턴 사용 유도로 기본 생성자 수동 생성 제한
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    private static final int MILEAGE_RATE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private String address;

    // 수신자명
    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    // 수신자 핸드폰 번호
    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;

    @Column(nullable = false)
    private String email;

    @Column(name = "delivery_message", length = 200)
    private String deliveryMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Builder
    public Order(Long userId, Long totalPrice, String address, String receiverName, String receiverPhone, String email, String deliveryMessage, OrderStatus status) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.address = address;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.email = email;
        this.deliveryMessage = deliveryMessage;
        this.status = status;
    }

    public Long calculateMileage() {
        return this.totalPrice * MILEAGE_RATE / 100;
    }
}
