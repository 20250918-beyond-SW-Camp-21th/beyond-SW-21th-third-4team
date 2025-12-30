package com.insilenceclone.backend.domain.product.service;

import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.domain.product.dto.ProductResponseDto;
import com.insilenceclone.backend.domain.product.entity.Product;
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

    public List<ProductResponseDto> getProducts(String category) {
        List<Product> products;
        if (category == null) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findByCategory(category);
        }

        return products.stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }


}
