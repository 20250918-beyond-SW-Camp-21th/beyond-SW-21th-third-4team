package com.insilenceclone.backend.domain.qna.controller.dto.response;

import com.insilenceclone.backend.domain.qna.entity.Qna;
import com.insilenceclone.backend.domain.qna.enums.QnaType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record QnaResponse(
        Long id,
        QnaType type,
        String title,
        String content,
        Long userId,
        LocalDateTime createdAt
) {
    public static QnaResponse from(@NotNull Qna qna) {
        return new QnaResponse(
                qna.getId(),
                qna.getType(),
                qna.getTitle(),
                qna.getContent(),
                qna.getUserId(),
                qna.getCreatedAt()
        );
    }
}
