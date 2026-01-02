package com.insilenceclone.backend.domain.qna.controller.dto.request;

import com.insilenceclone.backend.domain.qna.entity.Qna;
import com.insilenceclone.backend.domain.qna.enums.QnaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QnaCreateRequest(
        @NotBlank(message = "제목은 필수 입력 사항입니다.")
        String title,
        @NotBlank(message = "내용은 필수 입력 사항입니다.")
        String content,
        @NotNull(message = "질문 유형을 선택해주세요.")
        QnaType type,
        @NotNull(message = "사용자 ID는 필수입니다.")
        Long userId
) {
    public Qna toEntity() {
        return Qna.builder()
                .title(this.title)
                .content(this.content)
                .type(this.type)
                .userId(this.userId)
                .build();
    }
}
