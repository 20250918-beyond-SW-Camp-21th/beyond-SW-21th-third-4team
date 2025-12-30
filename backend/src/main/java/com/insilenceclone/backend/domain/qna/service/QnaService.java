package com.insilenceclone.backend.domain.qna.service;

import com.insilenceclone.backend.domain.qna.controller.dto.request.OrderCreateRequest;
import com.insilenceclone.backend.domain.qna.controller.dto.request.QnaRequest;
import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaListResponse;
import com.insilenceclone.backend.domain.qna.controller.dto.response.QnaResponse;
import com.insilenceclone.backend.domain.qna.entity.Qna;
import com.insilenceclone.backend.domain.qna.executor.QnaExecutor;
import com.insilenceclone.backend.domain.qna.usecase.reader.QnaReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class QnaService {

    private final QnaReader reader;
    private final QnaExecutor executor;

    // 게시글 작성
    public QnaResponse createQna(OrderCreateRequest req) {
        return executor.create(req);
    }

    // 게시글 목록 조회 (공지사항 선행, 최신순 정렬)
    public List<QnaListResponse> getQnaList() {
        return reader.readAllSorted();
    }

    // 게시글 상세 조회 (작성자 본인만 조회 가능)
    public QnaResponse getQna(QnaRequest req) {

        Qna qna = reader.readById(req.qnaId());

        qna.validateOwner(req.userId());

        return QnaResponse.from(qna);
    }

    // 답변 작성

}
