package com.insilenceclone.backend.domain.cart.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.cart.dto.request.CartItemAddRequestDto;
import com.insilenceclone.backend.domain.cart.service.CartService;
import com.insilenceclone.backend.domain.user.security.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ApiResponse<Void> cartItemAdd(
            @AuthenticationPrincipal CustomUser user,
            @Valid @RequestBody CartItemAddRequestDto request
    ) {
        cartService.addCartItem(user.getId(), request);
        return ApiResponse.success();
    }
}