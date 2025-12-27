package com.insilenceclone.backend.domain.mypage.service;

import com.insilenceclone.backend.domain.mypage.dto.OrderHistoryResponse;
import com.insilenceclone.backend.domain.mypage.dto.OrderItemResponse;
import com.insilenceclone.backend.domain.mypage.dto.UserInfoResponse;
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

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        return UserInfoResponse.from(user);
    }

    public List<OrderHistoryResponse> getOrderHistory(Long userId) {
        List<Order> orders = orderRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        return orders.stream()
                .map(order -> {
                    List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
                    List<OrderItemResponse> itemResponses = orderItems.stream()
                            .map(OrderItemResponse::from)
                            .collect(Collectors.toList());

                    return OrderHistoryResponse.of(order, itemResponses);
                })
                .collect(Collectors.toList());
    }
}