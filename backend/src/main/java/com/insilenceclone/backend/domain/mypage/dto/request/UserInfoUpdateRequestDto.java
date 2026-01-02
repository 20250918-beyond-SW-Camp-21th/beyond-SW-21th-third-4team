package com.insilenceclone.backend.domain.mypage.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserInfoUpdateRequestDto(
        @NotBlank(message = "name은 필수입니다.")
        @Size(min=2, max = 50, message = "name은 2~50자여야 합니다.")
        @Pattern(
                regexp = "^[가-힣]+$",
                message = "name은 한글만 입력할 수 있으며 공백, 숫자, 특수문자는 허용되지 않습니다."
        )
        String name,

        @NotBlank(message = "phone은 필수입니다.")
        @Size(max = 20, message = "phone은 20자 이하여야 합니다.")
        @Pattern(
                regexp = "^[0-9]+$",
                message = "phone은 숫자만 입력해야 합니다."
        )
        String phone,

        @NotBlank(message = "email은 필수입니다.")
        @Size(max = 100, message = "email은 100자 이하여야 합니다.")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "email 형식이 올바르지 않습니다."
        )
        String email,

        @Size(max = 255, message = "address는 255자 이하여야 합니다.")
        String address
) {
}
