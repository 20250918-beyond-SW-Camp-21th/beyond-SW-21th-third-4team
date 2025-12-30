package com.insilenceclone.backend.domain.product.dto;

import com.insilenceclone.backend.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    // 혹시 모를 큰 금액과 null을 안전하게 처리하기 위함.
    private Long price;
    /* String인 이유는 새로운 카테고리가 추가될때마다 ENUM은 Java 코드를 수정하고 서버 재배포해야함.
    *  String은 DB에 새로운 카테고리 값을 넣기만해도 반영됨. -> 초기 개발 단계나 카테고리가 바뀌는
    *  쇼핑몰 특성상 훨씬 유연하게 대처가능함.
    **/
    private String category;
    private String imageUrl;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.imageUrl = product.getImageUrl();
    }

}





