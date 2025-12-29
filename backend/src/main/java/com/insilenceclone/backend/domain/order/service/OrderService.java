package com.insilenceclone.backend.domain.order.service;

import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.domain.cart.entity.Cart;
import com.insilenceclone.backend.domain.cart.entity.CartItem;
import com.insilenceclone.backend.domain.cart.repository.CartItemRepository;
import com.insilenceclone.backend.domain.cart.repository.CartRepository;
import com.insilenceclone.backend.domain.order.dto.request.OrderCartRequestDto;
import com.insilenceclone.backend.domain.order.dto.request.OrderDeliveryDto;
import com.insilenceclone.backend.domain.order.dto.request.OrderDirectRequestDto;
import com.insilenceclone.backend.domain.order.dto.response.OrderDetailResponseDto;
import com.insilenceclone.backend.domain.order.dto.response.OrderItemDetailResponseDto;
import com.insilenceclone.backend.domain.order.entity.Order;
import com.insilenceclone.backend.domain.order.entity.OrderItem;
import com.insilenceclone.backend.domain.order.enums.OrderStatus;
import com.insilenceclone.backend.domain.order.repository.OrderItemRepository;
import com.insilenceclone.backend.domain.order.repository.OrderRepository;
import com.insilenceclone.backend.domain.product.entity.Product;
import com.insilenceclone.backend.domain.product.repository.ProductRepositoryTemp;
import com.insilenceclone.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepositoryTemp productRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository  cartItemRepository;
    // TODO: ProductRepository 의존성 주입

    /* 바로 구매(단일 상품) */
    @Transactional
    public Long createOrder(Long userId, OrderDirectRequestDto requestDto) {
        // <상품ID, 수량>
        Map<Long, Integer> productQuantityMap = new HashMap<>();
        productQuantityMap.put(requestDto.getProductId(), requestDto.getCount());

        return processOrder(userId, requestDto, productQuantityMap);
    }

    /* 장바구니 구매 */
    @Transactional
    public Long createOrderFromCart(Long userId, OrderCartRequestDto requestDto) {

         Cart cart = cartRepository.findByUserId(userId).orElseThrow(
                 () -> new BusinessException(ErrorCode.CART_NOT_FOUND)
         );

         List<CartItem> cartItemList = cartItemRepository.findByCartId(cart.getId());

         if(cartItemList.isEmpty()) {
             throw new BusinessException(ErrorCode.CART_EMPTY);
         }

         // <상품ID, 수량>
         Map<Long, Integer> productQuantityMap = new HashMap<>();
         for(CartItem cartItem : cartItemList) {
             productQuantityMap.put(cartItem.getProductId(), cartItem.getQuantity());
         }

         Long orderId = processOrder(userId, requestDto, productQuantityMap);
         // 기존 장바구니 삭제
         cartItemRepository.deleteAll(cartItemList);

         return orderId;
    }

    private Order createBaseOrder(Long userId, OrderDeliveryDto requestDto) {
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

        return order;
    }

    /* 중복 제거 목적의 주문 처리 공통 메소드 : 바로 구매와 장바구니 구매의 로직을 하나로 통합 */
    private Long processOrder(Long userId, OrderDeliveryDto deliveryDto, Map<Long, Integer> quantityMap) {
        if (!userRepository.existsById(userId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // Bulk 연산
        List<Product> products = productRepository.findAllById(quantityMap.keySet());

        // 요청한 상품 개수와 실제 DB 조회 개수(=ID의 개수)가 다르면 예외 처리
        if (products.size() != quantityMap.size()) {
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // 조회된 리스트를 상품 ID와 그에 해당하는 상품의 맵으로 변경
        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }

        Order order = createBaseOrder(userId, deliveryDto);

        // Orderitem 생성 및 저장
        for(Long productId : quantityMap.keySet()) {
            Product product = productMap.get(productId);
            int quantity = quantityMap.get(productId);

            // TODO: 재고 감소 로직

            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .productId(product.getId())
                    .quantity(quantity)
                    .unitPrice(product.getPrice())
                    .build();
            orderItemRepository.save(orderItem);

            order.addPrice(orderItem);
        }
        return order.getId();
    }

    @Transactional(readOnly = true)
    public OrderDetailResponseDto getOrderDetail(Long userId, Long orderId) {
        /*
        * 1. 주문이 존재하는지 검증
        * 2. 이 주문이 본인의 주문인지 검증
        * 3. orderId로 주문 목록을 모두 가져옴
        * 4. 상품 정보 조회
        * 5. OrderItem을 OrderDetailItemDto로 변경
        * */
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new BusinessException(ErrorCode.ORDER_NOT_FOUND)
        );

        if(!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.ORDER_ACCESS_DENIED);
        }

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);

        // 상품 정보 Bulk 조회
        List<Long> productIds = orderItems.stream()
                .map(OrderItem::getProductId)
                .toList();

        List<Product> products = productRepository.findAllById(productIds);

        // Map 사용
        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }

        List<OrderItemDetailResponseDto> itemDtos = orderItems.stream()
                .map(item -> {
                    Product product = productMap.get(item.getProductId());

                    return OrderItemDetailResponseDto.builder()
                            .productId(product.getId())
                            .productName(product != null ? product.getName() : "(삭제된 상품입니다.)")
                            .quantity(item.getQuantity())
                            .unitPrice(item.getUnitPrice())
                            .totalAmount(item.calculateTotalPrice())
                            .build();
                })
                .toList();

        return OrderDetailResponseDto.from(order, itemDtos);
    }
}
