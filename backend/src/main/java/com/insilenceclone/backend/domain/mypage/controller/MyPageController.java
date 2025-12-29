package com.insilenceclone.backend.domain.mypage.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.mypage.dto.OrderDTO;
import com.insilenceclone.backend.domain.mypage.dto.UserDTO;
import com.insilenceclone.backend.domain.mypage.service.MyPageService;
import com.insilenceclone.backend.domain.user.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/user-info")
    public ApiResponse<UserDTO> getUserInfo(
            @AuthenticationPrincipal CustomUser customUser) {

        UserDTO userInfo = myPageService.getUserInfo(customUser.getId());
        return ApiResponse.success(userInfo);
    }

    @GetMapping("/orders")
    public ApiResponse<List<OrderDTO>> getOrderHistory(
            @AuthenticationPrincipal CustomUser customUser,
            @RequestParam(required = false) String status) {

        List<OrderDTO> orderHistory = myPageService.getOrderHistory(customUser.getId(), status);
        return ApiResponse.success(orderHistory);
    }

    /*api 주소에 test가 들어간 엔드포인트들은 현재 로그인 기능이 없어서 임시로 permitall 권한을 준 상태다
    * 로그인 기능 구현 후에 삭제 할 예정*/

    @GetMapping("/test/user-info/{userId}")
    public ApiResponse<UserDTO> getUserInfoTest(@PathVariable Long userId) {
        UserDTO userInfo = myPageService.getUserInfo(userId);
        return ApiResponse.success(userInfo);
    }

    @GetMapping("/test/orders/{userId}")
    public ApiResponse<List<OrderDTO>> getOrderHistoryTest(
            @PathVariable Long userId,
            @RequestParam(required = false) String status) {
        List<OrderDTO> orderHistory = myPageService.getOrderHistory(userId, status);
        return ApiResponse.success(orderHistory);
    }
}