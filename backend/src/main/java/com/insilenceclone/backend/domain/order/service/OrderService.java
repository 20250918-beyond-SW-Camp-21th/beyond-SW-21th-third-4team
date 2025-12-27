package com.insilenceclone.backend.domain.order.service;

import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.domain.cart.entity.Cart;
import com.insilenceclone.backend.domain.cart.entity.CartItem;
import com.insilenceclone.backend.domain.cart.repository.CartItemRepository;
import com.insilenceclone.backend.domain.cart.repository.CartRepository;
import com.insilenceclone.backend.domain.order.dto.request.OrderRequestDto;
import com.insilenceclone.backend.domain.order.entity.Order;
import com.insilenceclone.backend.domain.order.entity.OrderItem;
import com.insilenceclone.backend.domain.order.enums.OrderStatus;
import com.insilenceclone.backend.domain.order.repository.OrderItemRepository;
import com.insilenceclone.backend.domain.order.repository.OrderRepository;
import com.insilenceclone.backend.domain.product.ProductRepositoryTemp;
import com.insilenceclone.backend.domain.product.entity.Product;
import com.insilenceclone.backend.domain.user.entity.User;
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

    /* 바로 구매 */
    @Transactional
    public Long createOrder(Long userId, OrderRequestDto requestDto) {

        // TODO: 임시 검증. 추후 USER_NOT_FOUND 에러코드 추가
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));

        Order order = createBaseOrder(userId, requestDto);

        Long totalPrice = 0L;

        /* 주문 생성 했으니, 주문 상품 생성 */
        for(OrderRequestDto.OrderProductDto productDto : requestDto.getProducts()) {
            // TODO: 에러코드 추가되면 수정
            Product product = productRepository.findById(productDto.getProductId()).orElseThrow(
                    () -> new IllegalArgumentException("상품이 존재하지 않습니다.")
            );

            // TODO: 재고 감소 로직 (Optional)

            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .productId(product.getId())
                    .quantity(productDto.getCount())
                    .unitPrice(product.getPrice())
                    .build();

            orderItemRepository.save(orderItem);

            totalPrice += orderItem.calculateTotalPrice();
        }

        order.updateTotalPrice(totalPrice);

        return order.getId();
    }

    /* 장바구니 구매 */
    @Transactional
    public Long createOrderFromCart(Long userId, OrderRequestDto requestDto) {

        /*
        * 흐름도
        * 1. userId 유효성 검사
        * 2. 장바구니를 CartRepository 사용하여 조회
        * 3. Order 객체 생성
        * 4. List 반복문을 돌려 OrderItem으로 이관
        * 5. Order 저장
        * 6. 장바구니 비우기 (deleteAll)
        * */

        // TODO: 에러코드 추가되면 수정
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));

        // TODO : CartRepository 사용하여 조회
         Cart cart = cartRepository.findByUserId(userId).orElseThrow(
                 () -> new BusinessException(ErrorCode.CART_NOT_FOUND)
         );

         List<CartItem> cartItemList = cartItemRepository.findByCartId(cart.getId());

         if(cartItemList.isEmpty()) {
             throw new BusinessException(ErrorCode.CART_EMPTY);
         }

        List<Long> productIds = cartItemList.stream()
                .map(CartItem::getProductId)
                .toList();

        List<Product> productList = productRepository.findAllById(productIds);

        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : productList) {
            productMap.put(product.getId(), product);
        }

        // Order 객체 생성
        Order order = createBaseOrder(userId, requestDto);

         Long totalPrice = 0L;

        // Cart 객체 불러오기(장바구니를 OrderItem으로 이관)
        for(CartItem cartItem : cartItemList) {
            Product product = productMap.get(cartItem.getProductId());

            // TODO: 에러코드 추가되면 수정
            if (product == null) {
                throw new IllegalArgumentException("상품 정보 없음");
            }

            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .productId(product.getId())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();

            orderItemRepository.save(orderItem);

            totalPrice += orderItem.calculateTotalPrice();
        }

        order.updateTotalPrice(totalPrice);

        cartItemRepository.deleteAll(cartItemList);

        return order.getId();
    }

    private Order createBaseOrder(Long userId, OrderRequestDto requestDto) {
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
}
