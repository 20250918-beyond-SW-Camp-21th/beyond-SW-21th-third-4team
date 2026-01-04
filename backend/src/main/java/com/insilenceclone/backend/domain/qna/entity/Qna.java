package com.insilenceclone.backend.domain.qna.entity;

import com.insilenceclone.backend.common.entity.BaseTimeEntity;
import com.insilenceclone.backend.common.exception.BusinessException;
import com.insilenceclone.backend.common.exception.ErrorCode;
import com.insilenceclone.backend.domain.qna.enums.QnaType;
import com.insilenceclone.backend.domain.qna.enums.QnaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QnaType type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long userId;

    private Long productId;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private Long answererId;

    private LocalDateTime answeredAt;

    @Enumerated(EnumType.STRING)
    private QnaStatus status = QnaStatus.WAITING;

    @Builder
    public Qna(QnaType type, String title, String content, Long userId, Long productId) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.productId = productId;
    }

    public void validateOwner(Long requestUserId) {

        if(this.type == QnaType.NOTICE) {
            return;
        }

        if(!this.userId.equals(requestUserId)){
            throw new BusinessException(ErrorCode.QNA_NOT_PERMITTED);
        }
    }

    public void writeAnswer(String answer) {
        this.answer = answer;
        this.answererId = 1L;
        this.answeredAt = LocalDateTime.now();
        this.status = QnaStatus.ANSWERED;
    }
}
