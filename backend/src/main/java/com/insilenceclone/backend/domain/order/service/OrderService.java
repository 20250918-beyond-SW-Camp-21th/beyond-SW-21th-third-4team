package com.insilenceclone.backend.domain.order.service;

import com.insilenceclone.backend.domain.order.dto.request.OrderRequestDto;
import com.insilenceclone.backend.domain.order.entity.Order;
import com.insilenceclone.backend.domain.order.entity.OrderItem;
import com.insilenceclone.backend.domain.order.enums.OrderStatus;
import com.insilenceclone.backend.domain.order.repository.OrderRepository;
import com.insilenceclone.backend.user.entity.User;
import com.insilenceclone.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    // TODO: ProductRepository 의존성 주입

    /* 바로 구매 */
    @Transactional
    public Long createOrder(Long userId, List<OrderItem> items, OrderRequestDto requestDto) {

        // 임시 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));

        Order order = Order.builder()
                .userId(userId)
                .address(requestDto.getAddress())
                .email(requestDto.getEmail())
                .receiverName(requestDto.getReceiverName())
                .receiverPhone(requestDto.getReceiverPhone())
                .deliveryMessage(requestDto.getDeliveryMessage())
                .status(OrderStatus.ORDERED)
                .totalPrice(0L)
                .build();
        orderRepository.save(order);

        for(OrderItem item : items){

        }

        return 0L;

    }

    /* 장바구니 구매 */
    @Transactional
    public Long createOrderFromCart(Long userId, OrderRequestDto requestDto) {

        /*
        * 1. userId 유효성 검사
        * 2. 장바구니를 CartRepository 사용하여 조회
        * 3. Order 객체 생성
        * 4. List 반복문을 돌려 OrderItem으로 이관
        * 5. Order 저장
        * 6. 장바구니 비우기 (deleteAll)
        * */

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));

        // TODO : CartRepository 사용하여 조회
        // ex. List<Cart> cartList = careRepository.findAllByUserId(userId).orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND);

        // Order 객체 생성
        Order order = Order.builder()
                .userId(userId)
                .address(requestDto.getAddress())
                .email(requestDto.getEmail())
                .receiverName(requestDto.getReceiverName())
                .receiverPhone(requestDto.getReceiverPhone())
                .deliveryMessage(requestDto.getDeliveryMessage())
                .status(OrderStatus.ORDERED)
                .totalPrice(0L)
                .build();

        // TODO : Cart 객체 불러오기
        // 장바구니를 OrderItem으로 이관

        return 0L;
    }
}
