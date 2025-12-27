package com.insilenceclone.backend.domain.user.controller;

import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.common.jwt.JwtTokenProvider;
import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.user.dto.SignUpRequestDto;
import com.insilenceclone.backend.domain.user.entity.User;
import com.insilenceclone.backend.domain.user.repository.UserRepository;
import com.insilenceclone.backend.domain.user.security.CustomUser;
import com.insilenceclone.backend.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    // 회원가입
    @PostMapping("/signup")
    public ApiResponse<Void> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return ApiResponse.success();
    }


    // ⚠️ 임시: 로그인 구현 전 테스트용 (나중에 꼭 삭제)
    @PostMapping("/dev-token")
    public ApiResponse<String> devToken(@RequestParam String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_INPUT)); // 대충 처리

        String token = jwtTokenProvider.createToken(user.getLoginId(), user.getId());
        return ApiResponse.success(token);
    }
    /**
     * ✅ 인증 확인 API (토큰 넣고 호출하면 현재 사용자 정보가 내려와야 함)
     */
    @GetMapping("/test/me")
    public ApiResponse<MeResponse> me(@AuthenticationPrincipal CustomUser principal) {
        // principal이 null이면 인증이 안 된 상태(=필터가 인증 객체 세팅 못함 or 토큰 없음)
        if (principal == null) {
            return ApiResponse.success(new MeResponse(null, null, false));
        }
        return ApiResponse.success(new MeResponse(principal.getId(), principal.getUsername(), true));
    }

    public record MeResponse(Long userId, String loginId, boolean authenticated) {}
}
