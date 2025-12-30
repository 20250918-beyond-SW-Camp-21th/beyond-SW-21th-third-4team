package com.insilenceclone.backend.domain.product.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.product.dto.ProductResponseDto;
import com.insilenceclone.backend.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 카테고리 조회 API(ex. /api/v1/products?category=OUTER)
    @GetMapping
    public ApiResponse<List<ProductResponseDto>> getProductList(
            @RequestParam(value = "category", required = false) String category
    ) {
        List<ProductResponseDto> productList = productService.getProducts(category);

        return ApiResponse.success(productList);
    }

}
