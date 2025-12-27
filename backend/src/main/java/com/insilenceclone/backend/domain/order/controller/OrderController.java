package com.insilenceclone.backend.domain.order.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.order.dto.request.OrderRequestDto;
import com.insilenceclone.backend.domain.order.service.OrderService;
import com.insilenceclone.backend.domain.user.security.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 바로 구매 API
    @PostMapping("/direct")
    public ApiResponse<Long> createOrder(
            @AuthenticationPrincipal CustomUser userDetails,
            @RequestBody @Valid OrderRequestDto requestDto
    ) {
        Long orderId = orderService.createOrder(userDetails.getId(), requestDto);

        return ApiResponse.success(orderId);
    }

    // 장바구니 구매 API
    @PostMapping("/cart")
    public ApiResponse<Long> createOrderFromCart(
            @AuthenticationPrincipal CustomUser userDetails,
            @RequestBody @Valid OrderRequestDto requestDto
    ) {
        Long orderId = orderService.createOrderFromCart(userDetails.getId(), requestDto);

        return ApiResponse.success(orderId);
    }
}
