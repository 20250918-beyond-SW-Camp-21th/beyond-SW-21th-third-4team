package com.insilenceclone.backend.domain.mypage.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.mypage.dto.OrderHistoryResponse;
import com.insilenceclone.backend.domain.mypage.dto.UserInfoResponse;
import com.insilenceclone.backend.domain.mypage.service.MyPageService;
import com.insilenceclone.backend.domain.user.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/user-info")
    public ApiResponse<UserInfoResponse> getUserInfo(
            @AuthenticationPrincipal CustomUser customUser) {

        UserInfoResponse userInfo = myPageService.getUserInfo(customUser.getId());
        return ApiResponse.success(userInfo);
    }

    @GetMapping("/orders")
    public ApiResponse<List<OrderHistoryResponse>> getOrderHistory(
            @AuthenticationPrincipal CustomUser customUser) {

        List<OrderHistoryResponse> orderHistory = myPageService.getOrderHistory(customUser.getId());
        return ApiResponse.success(orderHistory);
    }

    // TODO: 로그인 기능 구현 후 제거 - 테스트용 엔드포인트
    @GetMapping("/test/user-info/{userId}")
    public ApiResponse<UserInfoResponse> getUserInfoTest(@PathVariable Long userId) {
        UserInfoResponse userInfo = myPageService.getUserInfo(userId);
        return ApiResponse.success(userInfo);
    }

    // TODO: 로그인 기능 구현 후 제거 - 테스트용 엔드포인트
    @GetMapping("/test/orders/{userId}")
    public ApiResponse<List<OrderHistoryResponse>> getOrderHistoryTest(@PathVariable Long userId) {
        List<OrderHistoryResponse> orderHistory = myPageService.getOrderHistory(userId);
        return ApiResponse.success(orderHistory);
    }
}