package com.insilenceclone.backend.domain.cart.entity;

import com.insilenceclone.backend.common.entity.BaseTimeEntity;
import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cartitem",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_cartitem_cart_product",
                columnNames = {"cart_id", "product_id"}
        ))
public class CartItem extends BaseTimeEntity {
    public static final int MAX_QUANTITY = 5; // 최대 수량 제한
    public static final int MIN_QUANTITY = 1; // 최소 수량 제한

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cart_id", nullable = false)
    private Long cartId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;           // 수량

    private CartItem(Long cartId, Long productId, int quantity) {
        if (cartId == null) { throw new BusinessException(ErrorCode.CART_ID_REQUIRED);}
        if (productId == null) { throw new BusinessException(ErrorCode.CART_PRODUCT_ID_REQUIRED);}
        validateQuantity(quantity); // 생성 시 수량 검증

        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) { throw new BusinessException(ErrorCode.CART_QUANTITY_INVALID); }
        int total = this.quantity + amount;
        validateQuantity(total); // 합산 수량 검증
        this.quantity = total;
    }

    public void decreaseQuantity() {
        if (this.quantity <= MIN_QUANTITY) {
            return;
        }
        this.quantity -= 1;
    }

    private void validateQuantity(int quantity) {
        if (quantity < MIN_QUANTITY || quantity > MAX_QUANTITY) {
            throw new BusinessException(ErrorCode.CART_QUANTITY_INVALID);
        }
    }

    // 카트에 아이템 추가
    public static CartItem create(Long cartId, Long productId, int quantity) {
        return new CartItem(cartId, productId, quantity);
    }


}
