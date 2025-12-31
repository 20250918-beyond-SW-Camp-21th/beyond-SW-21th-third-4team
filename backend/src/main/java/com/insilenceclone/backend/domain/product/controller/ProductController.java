package com.insilenceclone.backend.domain.product.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.product.dto.ProductResponseDto;
import com.insilenceclone.backend.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "상품 API", description = "상품 목록 조회 및 필터링 관련 API")
public class ProductController {

    private final ProductService productService;

    // 카테고리 조회 API(ex. /api/v1/products?category=OUTER)
    @GetMapping
    @Operation(summary = "상품 목록 조회", description = "카테고리별 상품을 조회하거나 전체 목록을 조회합니다.")
    public ApiResponse<List<ProductResponseDto>> getProductList(
            @RequestParam(value = "category", required = false) String category
    ) {
        List<ProductResponseDto> productList = productService.getProducts(category);

        return ApiResponse.success(productList);
    }

}
