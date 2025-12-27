package com.insilenceclone.backend.domain.cart.entity;

import com.insilenceclone.backend.common.entity.BaseTimeEntity;
import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cart")
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    private Cart(Long userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.CART_USER_ID_REQUIRED);
        }

        this.userId = userId;
    }

    // 회원가입 시 장바구니 생성
    public static Cart create(Long userId) {
        return new Cart(userId);
    }

}
