package com.insilenceclone.backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 1. 공통 에러
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "C001", "입력값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 에러입니다.");

    // TODO(형욱) : 이 곳에서 각자 도메인에서 발생할 에러를 HttpStatus Enum을 참고하셔서 작성해주시면 됩니다 !! (2025-12-25)

    private final HttpStatus status;
    private final String code;
    private final String message;
}
