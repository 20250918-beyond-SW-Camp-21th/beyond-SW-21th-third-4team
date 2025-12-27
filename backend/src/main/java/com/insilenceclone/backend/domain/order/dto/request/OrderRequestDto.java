package com.insilenceclone.backend.domain.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
// 주문할 때 받는 정보
public class OrderRequestDto {

    @NotBlank(message = "주소는 필수로 입력해야 합니다.")
    private String address;

    @NotBlank(message = "수신자 성명은 필수로 입력해야 합니다.")
    private String receiverName;

    @NotBlank(message = "수신자 연락처는 필수로 입력해야 합니다.")
    private String receiverPhone;

    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    private String email;

    @NotEmpty(message = "주문 상품은 최소 1개 이상이어야 합니다.")
    private List<OrderProductDto> products = new ArrayList<>();

    private Long cartId;

    private String deliveryMessage;

    // 추후 확장성 대비
    private String paymentMethod;

    @Getter
    public static class OrderProductDto {

        @NotNull
        private Long productId;

        @Min(1)
        private int count;
    }
}
