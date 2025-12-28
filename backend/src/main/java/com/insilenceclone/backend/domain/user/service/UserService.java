package com.insilenceclone.backend.domain.user.service;

import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.common.jwt.JwtTokenProvider;
import com.insilenceclone.backend.domain.user.dto.request.LoginRequestDto;
import com.insilenceclone.backend.domain.user.dto.request.SignUpRequestDto;
import com.insilenceclone.backend.domain.user.dto.response.TokenResponseDto;
import com.insilenceclone.backend.domain.user.entity.User;
import com.insilenceclone.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public void signUp(SignUpRequestDto request) {

        if(userRepository.existsByLoginId(request.loginId())){
            throw new BusinessException(ErrorCode.USER_LOGIN_ID_DUPLICATED);
        }

        LocalDate today = LocalDate.now();

        // 만 14세 이상 가입 조건
        int age = Period.between(request.birthDate(), today).getYears();
        if (age < 14) {
            throw new BusinessException(ErrorCode.USER_UNDER_MIN_AGE);
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .loginId(request.loginId())
                .password(encodedPassword)
                .name(request.name())
                .phone(request.phone())
                .email(request.email())
                .birthDate(request.birthDate())
                .address(request.address())
                .build();

        userRepository.save(user);
    }

    // 로그인
    @Transactional(readOnly = true)
    public TokenResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.AUTH_LOGIN_FAILED));

        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new BusinessException(ErrorCode.AUTH_LOGIN_FAILED);
        }

        String accessToken = jwtTokenProvider.createToken(user.getLoginId(),user.getId());
        return new TokenResponseDto(accessToken);
    }
}
