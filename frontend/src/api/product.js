import http from './http.js';

/**
 * 상품 상세 조회
 * GET /api/v1/product-details/{productId}
 */
export const fetchProductDetail = (productId) => {
    return http.get(`/product-details/${productId}`);
};
