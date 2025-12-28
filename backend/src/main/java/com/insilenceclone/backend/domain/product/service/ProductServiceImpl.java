package com.insilenceclone.backend.domain.product.service;

import com.insilenceclone.backend.domain.product.dto.ProductResponseDto;
import com.insilenceclone.backend.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponseDto> getProducts(String category) {
        // 카테고리가 있으면 필터링 조회
        if (category != null && !category.isEmpty()) {
            return productRepository.findByCategory(category).stream()
                    .map(ProductResponseDto::from)
                    .collect(Collectors.toList());
        }

        // 없으면 전체 조회
        return productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }
}
