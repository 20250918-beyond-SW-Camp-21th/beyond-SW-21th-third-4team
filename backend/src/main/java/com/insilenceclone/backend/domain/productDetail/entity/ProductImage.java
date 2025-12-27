package com.insilenceclone.backend.domain.productImage.entity;

import com.insilenceclone.backend.common.entity.BaseTimeEntity;
import com.insilenceclone.backend.domain.productImage.enums.ProductImageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_images")
public class ProductImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    // 이미지 정렬
    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ProductImageStatus status;

    public ProductImage(Long productId, String imageUrl,
                        int sortOrder) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
        this.status = ProductImageStatus.ACTIVE;
    }
}
