package com.insilenceclone.backend.domain.cart.service;

import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.domain.cart.dto.request.CartItemAddRequestDto;
import com.insilenceclone.backend.domain.cart.dto.response.CartItemResponseDto;
import com.insilenceclone.backend.domain.cart.entity.Cart;
import com.insilenceclone.backend.domain.cart.entity.CartItem;
import com.insilenceclone.backend.domain.cart.repository.CartItemRepository;
import com.insilenceclone.backend.domain.cart.repository.CartRepository;
import com.insilenceclone.backend.domain.product.ProductRepositoryTemp;
import com.insilenceclone.backend.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // TODO: 임시 레포지토리 이므로 추후 수정
    private final ProductRepositoryTemp productRepositoryTemp;

    @Transactional
    public void addCartItem(Long userId, CartItemAddRequestDto request) {

        // TODO: 임시 레포지토리, 에러코드 이므로 추후 수정
        if(!productRepositoryTemp.existsById(request.productId()))
        {
            throw new BusinessException(ErrorCode.TEMP_PRODUCT_DOES_NOT_EXIST);
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.create(userId)));

        Long cartId = cart.getId();
        Long productId = request.productId();
        int quantity = request.quantity();

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElse(null);

        // 장바구니에 존재하지 않는 상품일 경우 장바구니에 상품 추가
        if (cartItem == null) {
            CartItem newCartItem = CartItem.create(cartId, productId, quantity);
            cartItemRepository.save(newCartItem);
            return;
        }

        // 장바구니에 존재하는 상품일 경우 수량 증가
        // JPA dirty checking으로 update 반영
        cartItem.increaseQuantity(quantity);
    }

    @Transactional(readOnly = true)
    public List<CartItemResponseDto> viewMyCartItems(Long userId) {
/*     1. userId로 cart 찾기 (없으면 빈 리스트)
       2. cartId로 cartItem 리스트 조회(최근 담은순으로 정렬)
       3. cartItem들에서 productIds 추출
       4. findAllById(productIds)로 조회
       5. Map<productId, Product>로 바꾸고 cartItem과 매칭해서 응답 DTO 리스트 만들기
       */
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(null);
        if (cart == null) return List.of();  // 장바구니 자체가 없으면 비어있다고 처리

        List<CartItem> cartItems = cartItemRepository.findByCartIdOrderByUpdatedAtDesc(cart.getId());

        // 장바구니가 비었으면 빈 리스트 반환
        if (cartItems.isEmpty()) return List.of();

        // productId 추출
        List<Long> productIds = cartItems.stream()
                .map(CartItem::getProductId)
                .distinct()
                .toList();

        List<Product> products = productRepositoryTemp.findAllById(productIds);

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        return cartItems.stream()
                .map(ci -> {
                    Product p = productMap.get(ci.getProductId());
                    return new CartItemResponseDto(
                            p.getId(),
                            p.getName(),
                            p.getPrice(),
                            p.getImageUrl(),
                            ci.getQuantity()
                    );
                })
                .toList();
    }

}
