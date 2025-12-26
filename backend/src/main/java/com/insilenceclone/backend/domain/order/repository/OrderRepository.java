package com.insilenceclone.backend.domain.order.repository;

import com.insilenceclone.backend.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // PK 간접 참조로 주문 내역 조회 메소드 추가
    List<Order> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
