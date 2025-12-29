package com.insilenceclone.backend.domain.productDetail.service;

import com.insilenceclone.backend.domain.productDetail.controller.dto.ProductDetailResponse;
import com.insilenceclone.backend.domain.productDetail.usecase.reader.ProductDetailReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductDetailServiceTests {

    @Mock
    private ProductDetailReader reader;

    @InjectMocks
    private ProductDetailService service;

    @Test
    void getProductDetailByIdTest() {

        // given
        Long productId = 1L;
        ProductDetailResponse mockResponse = new ProductDetailResponse(
                productId, "복실복실 양털 코트", "털이 너무 빠져서 탈모 치료에 제격", 748000L, "CottonCoat.jpg", List.of("CottonCoatBackSide.jpg")
        );

        given(reader.read(productId)).willReturn(mockResponse);

        // when
        ProductDetailResponse result = service.getDetail(productId);

        // then
        assertThat(result.name()).isEqualTo("복실복실 양털 코트");
        assertThat(result.productId()).isEqualTo(productId);
    }
}