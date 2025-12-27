package com.insilenceclone.backend.domain.mypage.dto;

import com.insilenceclone.backend.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse {

    private Long id;
    private String loginId;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String address;

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .build();
    }
}