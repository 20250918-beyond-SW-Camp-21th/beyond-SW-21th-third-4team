// src/api/cart.js
import http from './http.js'

/**
 * 장바구니 담기
 * POST /api/v1/cart/items
 * body: { productId: Long, quantity: Int }
 */
export const addCartItem = (productId, quantity = 1) => {
    return http.post('/cart/items', { productId, quantity })
}

/**
 * 내 장바구니 조회
 * GET /api/v1/cart/my
 */
export const fetchMyCart = () => {
    return http.get('/cart/my')
}

/**
 * 장바구니 선택 삭제 (여러 개)
 * POST /api/v1/cart/items/delete
 * body: { cartItemIds: [Long, ...] }
 */
export const deleteCartItems = (cartItemIds) => {
    return http.post('/cart/items/delete', { cartItemIds })
}

/**
 * 수량 증가 (1씩)
 * POST /api/v1/cart/{cartItemId}/increase
 */
export const increaseCartItem = (cartItemId) => {
    return http.post(`/cart/${cartItemId}/increase`)
}

/**
 * 수량 감소 (1씩, 최소 1 유지)
 * POST /api/v1/cart/{cartItemId}/decrease
 */
export const decreaseCartItem = (cartItemId) => {
    return http.post(`/cart/${cartItemId}/decrease`)
}
