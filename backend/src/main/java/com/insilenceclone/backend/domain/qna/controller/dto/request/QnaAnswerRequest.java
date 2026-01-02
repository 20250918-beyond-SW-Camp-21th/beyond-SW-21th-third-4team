package com.insilenceclone.backend.domain.qna.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QnaAnswerRequest(
        @NotNull Long qnaId,       // 어떤 글에 답변할지 ID
        @NotBlank String answer    // 답변 내용
) {}
