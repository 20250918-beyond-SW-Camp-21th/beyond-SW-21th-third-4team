package com.insilenceclone.backend.domain.user.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record SignUpRequestDto(
        @NotBlank(message = "loginId는 필수입니다.")
        @Size(min = 6, max = 15, message = "loginId는 6~15자여야 합니다.")
        @Pattern(
                regexp = "^[a-zA-Z0-9]+$",
                message = "loginId는 영문과 숫자만 사용할 수 있습니다."
        )
        String loginId,

        @NotBlank(message = "password는 필수입니다.")
        @Size(min = 8, max = 20, message = "password는 8~20자여야 합니다.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]+$",
                message = "password는 영문과 숫자를 모두 포함해야 합니다."
        )
        String password,

        @NotBlank(message = "name은 필수입니다.")
        @Size(min=2,max = 50, message = "name은 2~50자여야 합니다.")
        @Pattern(
                regexp = "^[가-힣a-zA-Z]+$",
                message = "name은 한글/영문만 입력할 수 있으며 공백, 숫자, 특수문자는 허용되지 않습니다."
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

        @NotNull(message = "birthDate는 필수입니다.")
        @PastOrPresent(message = "birthDate는 오늘 이전 날짜여야 합니다.")
        LocalDate birthDate,

        // ✅ 주소만 null 허용
        @Size(max = 255, message = "address는 255자 이하여야 합니다.")
        String address
) {
}
