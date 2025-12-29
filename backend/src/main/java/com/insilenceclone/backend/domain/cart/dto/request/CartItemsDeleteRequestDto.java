package com.insilenceclone.backend.domain.cart.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartItemsDeleteRequestDto(

        @NotEmpty(message = "선택된 상품이 없습니다.")
        List<@NotNull(message = "cartItem은 null일 수 없습니다.") Long> cartItemIds
) {
}
