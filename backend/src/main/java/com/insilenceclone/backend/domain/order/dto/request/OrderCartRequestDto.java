package com.insilenceclone.backend.domain.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCartRequestDto extends OrderDeliveryDto {

    @NotNull
    private Long cartId;
}
