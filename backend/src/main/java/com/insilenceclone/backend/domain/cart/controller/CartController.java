package com.insilenceclone.backend.domain.cart.controller;

import com.insilenceclone.backend.common.response.ApiResponse;
import com.insilenceclone.backend.domain.cart.dto.request.CartItemAddRequestDto;
import com.insilenceclone.backend.domain.cart.dto.response.CartItemResponseDto;
import com.insilenceclone.backend.domain.cart.service.CartService;
import com.insilenceclone.backend.domain.user.security.CustomUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/my")
    public ApiResponse<List<CartItemResponseDto>> getMyCartItem(@AuthenticationPrincipal CustomUser user) {
        List<CartItemResponseDto> myCart = cartService.viewMyCartItems(user.getId());
        return ApiResponse.success(myCart);
    }
}