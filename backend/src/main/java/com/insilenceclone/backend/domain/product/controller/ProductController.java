package com.insilenceclone.backend.domain.product.controller;

import com.insilenceclone.backend.domain.product.dto.ProductResponseDto;
import com.insilenceclone.backend.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 메인 페이지
    @GetMapping("/")
    public String main() {
        return "main";
    }


    // ex. /products?category=OUTER
    @GetMapping("/products")
    public String list(@RequestParam(value = "category", required = false) String category, Model model) {

        List<ProductResponseDto> products = productService.getProducts(category);
        model.addAttribute("products", products);

        // 사용자가 어떤 카테고리 보는지 HTML에게 알려줌
        model.addAttribute("activeCategory", category);

        return "product/list"; // HTML
    }
}
