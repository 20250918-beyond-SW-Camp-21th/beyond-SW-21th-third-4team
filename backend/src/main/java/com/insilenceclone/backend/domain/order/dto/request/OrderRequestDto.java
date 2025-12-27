package com.insilenceclone.backend.domain.order.dto.request;

import com.insilenceclone.backend.domain.order.entity.OrderItem;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
// 주문할 때 받는 정보
public class OrderRequestDto {

    List<OrderItem> items = new ArrayList<>();

    @NotBlank(message = "주소는 필수로 입력해야 합니다.")
    private String address;

    @NotBlank(message = "수신자 성명은 필수로 입력해야 합니다.")
    private String receiverName;

    @NotBlank(message = "수신자 연락처는 필수로 입력해야 합니다.")
    private String receiverPhone;

    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    private String email;

    private String deliveryMessage;

    // 추후 확장성 대비
    private String paymentMethod;
}
