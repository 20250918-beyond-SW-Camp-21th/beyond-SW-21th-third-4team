package com.insilenceclone.backend.domain.order.repository;

import com.insilenceclone.backend.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 특정 주문에 담긴 상품 조회 메소드
    List<OrderItem> findAllByOrderId(Long orderId);
}
