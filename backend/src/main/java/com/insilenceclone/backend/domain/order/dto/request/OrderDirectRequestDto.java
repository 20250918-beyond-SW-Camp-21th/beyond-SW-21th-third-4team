package com.insilenceclone.backend.domain.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDirectRequestDto extends OrderDeliveryDto {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @Min(value = 1, message = "수량은 최소 1개 있어야 합니다.")
    private int count;

}
