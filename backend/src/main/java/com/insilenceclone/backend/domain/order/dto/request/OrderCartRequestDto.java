package com.insilenceclone.backend.domain.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderCartRequestDto extends OrderDeliveryDto {

    @NotEmpty(message = "주문 할 상품은 최소 한 개 이상 선택해야 합니다.")
    private List<Long> cartItemIds = new ArrayList<>();
}
