package com.insilenceclone.backend.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        ErrorCode errorCode = ErrorCode.AUTH_UNAUTHORIZED;

        Throwable cause = authException.getCause();
        if (cause instanceof BusinessException be) {
            errorCode = be.getErrorCode();
        }

        ErrorResponse body = ErrorResponse.of(errorCode);

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
