package com.insilenceclone.backend.domain.cart.service;

import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.domain.cart.dto.request.CartItemAddRequestDto;
import com.insilenceclone.backend.domain.cart.entity.Cart;
import com.insilenceclone.backend.domain.cart.entity.CartItem;
import com.insilenceclone.backend.domain.cart.repository.CartItemRepository;
import com.insilenceclone.backend.domain.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
