package com.insilenceclone.backend.domain.order.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.order.dto.request.OrderCartRequestDto;
import com.insilenceclone.backend.domain.order.dto.request.OrderDirectRequestDto;
import com.insilenceclone.backend.domain.order.dto.response.OrderDetailResponseDto;
import com.insilenceclone.backend.domain.order.service.OrderService;
import com.insilenceclone.backend.domain.user.security.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 바로 구매 API
    @PostMapping("/direct")
    public ApiResponse<Long> createOrder(
            @AuthenticationPrincipal CustomUser userDetails,
            @RequestBody @Valid OrderDirectRequestDto requestDto
    ) {
        Long orderId = orderService.createOrder(userDetails.getId(), requestDto);

        return ApiResponse.success(orderId);
    }

    // 장바구니 구매 API
    @PostMapping("/cart")
    public ApiResponse<Long> createOrderFromCart(
            @AuthenticationPrincipal CustomUser userDetails,
            @RequestBody @Valid OrderCartRequestDto requestDto
    ) {
        Long orderId = orderService.createOrderFromCart(userDetails.getId(), requestDto);

        return ApiResponse.success(orderId);
    }


    // 주문 상세 조회 API
    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetailResponseDto> getOrderDetail(
            @AuthenticationPrincipal CustomUser userDetails,
            @PathVariable Long orderId
    ) {
        OrderDetailResponseDto responseDto = orderService.getOrderDetail(userDetails.getId(), orderId);

        return ApiResponse.success(responseDto);
    }
}
