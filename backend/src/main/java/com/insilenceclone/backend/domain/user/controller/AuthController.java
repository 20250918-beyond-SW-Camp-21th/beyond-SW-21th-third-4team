package com.insilenceclone.backend.domain.user.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.user.dto.request.LoginRequestDto;
import com.insilenceclone.backend.domain.user.dto.request.SignUpRequestDto;
import com.insilenceclone.backend.domain.user.dto.response.TokenResponseDto;
import com.insilenceclone.backend.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<Void> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return ApiResponse.success();
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        TokenResponseDto token = userService.login(loginRequestDto);
        return ApiResponse.success(token);
    }
}
