package com.insilenceclone.backend.domain.productDetail.controller;

import com.insilenceclone.backend.domain.productDetail.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product-details")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductDetailService service;

    // 상품 상세 페이지 조회
    @GetMapping("/{productId}")
    public void getDetail(@PathVariable Long productId) {
        // TODO: 상품 상세 조회 호출
    }

    // 장바구니 담기
    @PostMapping("/{productId}/cart")
    public void addToCart(@PathVariable Long productId) {
        // TODO: 장바구니 호출
    }

    // 바로 구매
    @PostMapping("/{productId}/order")
    public void order(@PathVariable Long productId) {
        // TODO: 바로 구매 호출
    }
}
