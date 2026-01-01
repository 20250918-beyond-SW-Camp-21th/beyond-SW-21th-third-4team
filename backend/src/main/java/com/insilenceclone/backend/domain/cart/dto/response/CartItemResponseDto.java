package com.insilenceclone.backend.domain.cart.dto.response;

public record CartItemResponseDto (
        Long cartItemId,
        Long productId,
        String name,
        Long price,
        String imageUrl,
        int quantity
){
}
