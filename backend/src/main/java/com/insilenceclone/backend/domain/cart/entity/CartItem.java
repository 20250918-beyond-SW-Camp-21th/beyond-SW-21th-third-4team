package com.insilenceclone.backend.domain.cart.entity;

import com.insilenceclone.backend.common.entity.BaseTimeEntity;
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
        if (cartId == null) throw new IllegalArgumentException("cartId must not be null");
        if (productId == null) throw new IllegalArgumentException("productId must not be null");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");

        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static CartItem create(Long cartId, Long productId, int quantity) {
        return new CartItem(cartId, productId, quantity);
    }

    // 같은 상품 수량 증가
    public void increaseQuantity() {
        this.quantity ++;
    }
}
