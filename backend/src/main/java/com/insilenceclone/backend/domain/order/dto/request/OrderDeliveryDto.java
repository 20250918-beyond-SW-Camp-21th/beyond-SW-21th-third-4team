package com.insilenceclone.backend.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDeliveryDto {

    @NotBlank(message = "주소는 필수로 입력해야 합니다.")
    private String address;

    @NotBlank(message = "수신자 성명은 필수로 입력해야 합니다.")
    private String receiverName;

    @NotBlank(message = "수신자 연락처는 필수로 입력해야 합니다.")
    private String receiverPhone;

    @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
    private String email;

    private String deliveryMessage;

}
