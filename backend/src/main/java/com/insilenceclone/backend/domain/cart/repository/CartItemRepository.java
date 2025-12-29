package com.insilenceclone.backend.domain.cart.repository;

import com.insilenceclone.backend.domain.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 장바구니에 담긴 아이템 전체 조회
    List<CartItem> findByCartId(Long cartId);

    // 장바구니에 담긴 최신순으로 전체 조회
    List<CartItem> findByCartIdOrderByUpdatedAtDesc(Long cartId);

    // 카트에 담을려는 상품이 이미 존재하는지 조회
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    // 특정 상품 삭제
    void deleteByCartIdAndProductId(Long cartId, Long productId);
    // 전체 상품 삭제
    void deleteByCartId(Long cartId);
}
