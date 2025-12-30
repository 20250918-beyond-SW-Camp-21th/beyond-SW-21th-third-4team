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
        if (quantity <= 0) {throw new BusinessException(ErrorCode.CART_QUANTITY_INVALID);}

        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // 카트에 아이템 추가
    public static CartItem create(Long cartId, Long productId, int quantity) {
        return new CartItem(cartId, productId, quantity);
    }

    // 같은 상품 수량 증가
    public void increaseQuantity(int amount) {
        if (amount <= 0) {throw new BusinessException(ErrorCode.CART_QUANTITY_INVALID);}
        this.quantity += amount;
    }

    public void decreaseQuantity() {
        if (this.quantity <= 1) {
            return; // 최소 1 유지: 변경 없음
        }
        this.quantity -= 1;
    }
}
