package com.insilenceclone.backend.domain.qna.controller.dto.request;

import org.springframework.stereotype.Component;

public record QnaRequest (
        Long qnaId,
        Long userId
) {
}
