package com.insilenceclone.backend.domain.mypage.service;

import com.insilenceclone.backend.domain.mypage.dto.UserInfoResponse;
import com.insilenceclone.backend.domain.user.entity.User;
import com.insilenceclone.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;

    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        return UserInfoResponse.from(user);
    }
}