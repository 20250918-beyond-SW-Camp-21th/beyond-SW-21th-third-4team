package com.insilenceclone.backend.domain.cart.repository;

import com.insilenceclone.backend.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 유저 장바구니 조회
    Optional<Cart> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

}
