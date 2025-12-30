package com.insilenceclone.backend.domain.productDetail.usecase.reader;

import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.domain.product.repository.ProductRepositoryTemp;
import com.insilenceclone.backend.domain.productDetail.controller.dto.ProductDetailResponse;
import com.insilenceclone.backend.domain.productDetail.entity.ProductImage;
import com.insilenceclone.backend.domain.productDetail.enums.ProductImageStatus;
import com.insilenceclone.backend.domain.productDetail.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDetailReaderImpl implements ProductDetailReader {

    private final ProductRepositoryTemp productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public ProductDetailResponse read(Long productId) {

        var info = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        List<String> imageUrls =
                productImageRepository.findByProductIdAndStatusOrderBySortOrderAsc(
                        productId, ProductImageStatus.ACTIVE)
                        .stream()
                        .map(ProductImage::getImageUrl)
                        .toList();

        if(imageUrls.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_IMAGE_NOT_FOUND);
        }

        return ProductDetailResponse.of(info, imageUrls);
    }
}
