package com.insilenceclone.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 1. 공통 에러
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "C001", "입력값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 에러입니다."),

    // TODO(형욱) : 이 곳에서 각자 도메인에서 발생할 에러를 HttpStatus Enum을 참고하셔서 작성해주시면 됩니다 !! (2025-12-25)

    // auth
    AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증이 필요합니다."),
    AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, "A002", "접근 권한이 없습니다."),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "유효하지 않은 토큰입니다."),
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A004", "토큰이 만료되었습니다."),
    AUTH_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "A005", "아이디 또는 비밀번호가 올바르지 않습니다."),

    // user
    USER_LOGIN_ID_DUPLICATED(HttpStatus.BAD_REQUEST, "U001", "이미 사용 중인 아이디입니다."),
    USER_UNDER_MIN_AGE(HttpStatus.BAD_REQUEST, "U002", "만 14세 이상만 가입할 수 있습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U003", "사용자가 존재하지 않습니다."),


    // cart
    CART_USER_ID_REQUIRED(HttpStatus.BAD_REQUEST,"CA001","userId는 필수입니다."),
    CART_ID_REQUIRED(HttpStatus.BAD_REQUEST, "CA002", "cartId는 필수입니다."),
    CART_PRODUCT_ID_REQUIRED(HttpStatus.BAD_REQUEST, "CA003", "productId는 필수입니다."),
    CART_QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "CA004", "quantity는 1 이상이어야 합니다."),
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "C005", "장바구니가 없습니다."),
    CART_EMPTY(HttpStatus.NOT_FOUND, "C006", "장바구니가 비어있습니다."),

    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "상품이 존재하지 않습니다."),

    // temp(임시)
    TEMP_PRODUCT_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "T001", "존재하지 않는 상품입니다."),

    // Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O001", "주문 정보가 존재하지 않습니다."),
    ORDER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "O002", "주문 조회의 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
