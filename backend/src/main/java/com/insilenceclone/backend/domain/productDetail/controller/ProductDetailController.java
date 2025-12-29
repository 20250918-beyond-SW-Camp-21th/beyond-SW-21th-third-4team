package com.insilenceclone.backend.domain.productDetail.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.productDetail.controller.dto.ProductDetailResponse;
import com.insilenceclone.backend.domain.productDetail.service.ProductDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product-details")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductDetailService service;

    // 상품 상세 페이지 조회
    @GetMapping("/{productId}")
    public ApiResponse<ProductDetailResponse> getDetail(
            @PathVariable Long productId
    ) {
        ProductDetailResponse response = service.getDetail(productId);
        return ApiResponse.success(response);
    }
}