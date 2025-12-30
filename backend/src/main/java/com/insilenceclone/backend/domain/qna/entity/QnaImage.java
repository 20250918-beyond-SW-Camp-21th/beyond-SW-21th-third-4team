package com.insilenceclone.backend.domain.qna.entity;

import com.insilenceclone.backend.common.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long qnaId;

    @Column(nullable = false)
    private String imageUrl;

    public QnaImage(Long qnaId, String imageUrl) {
        this.qnaId = qnaId;
        this.imageUrl = imageUrl;
    }
}
