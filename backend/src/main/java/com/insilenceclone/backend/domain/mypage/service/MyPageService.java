package com.insilenceclone.backend.domain.mypage.service;

import com.insilenceclone.backend.domain.mypage.dto.OrderDTO;
import com.insilenceclone.backend.domain.mypage.dto.OrderItemDTO;
import com.insilenceclone.backend.domain.mypage.dto.UserDTO;
import com.insilenceclone.backend.domain.order.entity.Order;
import com.insilenceclone.backend.domain.order.entity.OrderItem;
import com.insilenceclone.backend.domain.order.repository.OrderItemRepository;
import com.insilenceclone.backend.domain.order.repository.OrderRepository;
import com.insilenceclone.backend.domain.user.entity.User;
import com.insilenceclone.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public UserDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        return UserDTO.from(user);
    }

    public List<OrderDTO> getOrderHistory(Long userId, String statusFilter) {
        List<Order> orders = orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        if (statusFilter != null) {
            orders = filterOrdersByStatus(orders, statusFilter);
        }

        return orders.stream()
                .map(order -> {
                    List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
                    List<OrderItemDTO> itemResponses = orderItems.stream()
                            .map(OrderItemDTO::from)
                            .collect(Collectors.toList());

                    return OrderDTO.of(order, itemResponses);
                })
                .collect(Collectors.toList());
    }

    private List<Order> filterOrdersByStatus(List<Order> orders, String statusFilter) {
        return orders.stream()
                .filter(order -> {
                    switch (statusFilter.toLowerCase()) {
                        case "active":
                            // 진행중인 주문: 주문완료, 배송준비중, 배송중
                            return order.getStatus().name().equals("ORDERED")
                                    || order.getStatus().name().equals("PREPARING")
                                    || order.getStatus().name().equals("SHIPPING");
                        case "completed":
                            return order.getStatus().name().equals("DELIVERED");
                        case "cancelled":
                            return order.getStatus().name().equals("CANCELLED");
                        default:
                            return true;
                    }
                })
                .collect(Collectors.toList());
    }
}