package com.insilenceclone.backend.common.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String code = "A001";
        String message = "인증이 필요합니다.";

        Throwable cause = authException.getCause();
        if (cause instanceof com.insilenceclone.backend.common.exception.BusinessException be) {
            code = be.getErrorCode().getCode();
            message = be.getErrorCode().getMessage();
        }

        String timestamp = java.time.LocalDateTime.now().toString();
        int status = HttpServletResponse.SC_UNAUTHORIZED;

        String jsonResponse = "{"
                + "\"timestamp\":\"" + timestamp + "\","
                + "\"status\":" + status + ","
                + "\"code\":\"" + code + "\","
                + "\"message\":\"" + message + "\""
                + "}";

        response.getWriter().write(jsonResponse);
    }
}
